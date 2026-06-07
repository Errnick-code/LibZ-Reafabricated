package net.libz.mixin.config;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.JsonElement;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.JsonObject;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.api.Marshaller;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.impl.MarshallerImpl;
import net.libz.api.ConfigSync;

@SuppressWarnings("deprecation")
@Mixin(MarshallerImpl.class)
public class MarshallerImplMixin {

    private Field field = null;

    @Inject(method = "serialize", at = @At(value = "INVOKE", target = "Ljava/lang/reflect/Field;getName()Ljava/lang/String;"), locals = LocalCapture.CAPTURE_FAILSOFT, remap = false)
    private void serializeMixin(Object obj, CallbackInfoReturnable<JsonElement> info, BiFunction<Object, Marshaller, JsonElement> serializer, JsonObject result, Field var4[], int var5, int var6,
            Field f) {
        this.field = f;
    }

    @Redirect(method = "serialize", at = @At(value = "INVOKE", target = "Lme/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonObject;put(Ljava/lang/String;Lme/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonElement;)Lme/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonElement;"), remap = false)
    private JsonElement putMixin(JsonObject jsonObject, String name, JsonElement jsonElement) {
        if (this.field != null && this.field.getName().equals(name)) {
            if (this.field.getAnnotation(ConfigSync.ClientOnly.class) != null) {
                return jsonObject.put(name, jsonElement, "client only");
            }
        }
        return jsonObject.put(name, jsonElement);
    }

    @Redirect(method = "serialize", at = @At(value = "INVOKE", target = "Lme/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonObject;put(Ljava/lang/String;Lme/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonElement;Ljava/lang/String;)Lme/shedaniel/cloth/clothconfig/shadowed/blue/endless/jankson/JsonElement;"), remap = false)
    private JsonElement putMixin(JsonObject jsonObject, String name, JsonElement jsonElement, String comment) {
        if (this.field != null && this.field.getName().equals(name)) {
            if (this.field.getAnnotation(ConfigSync.ClientOnly.class) != null) {
                return jsonObject.put(name, jsonElement, comment + ", client only");
            }
        }
        return jsonObject.put(name, jsonElement, comment);
    }

}
