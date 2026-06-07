package net.libz.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;

/**
 * ИСПРАВЛЕНО: В 1.21.11 WorldOpenFlows.confirmWorldCreation имеет другое поведение
 * чем оригинальный IntegratedServerLoader.tryLoad в 1.21.1.
 * 
 * Оригинал (1.21.1): отключал диалог "восстановить мир?" - безопасно
 * Порт (1.21.11): confirmWorldCreation контролирует создание мира целиком.
 * Возврат false может прерывать загрузку мира и ломать GUI flow.
 *
 * Решение: mixin отключён. Диалог восстановления снова будет показываться,
 * но GUI будет работать корректно.
 *
 * Если нужно вернуть поведение - найдите правильный метод в WorldOpenFlows
 * который соответствует tryLoad (показ диалога BackupConfirmDialog).
 */
@Environment(EnvType.CLIENT)
@Mixin(value = WorldOpenFlows.class, priority = 999)
public class IntegratedServerLoaderMixin {

    // ЗАКОММЕНТИРОВАНО - confirmWorldCreation это не тот метод!
    // В 1.21.1 был IntegratedServerLoader.tryLoad - первый boolean это "showBackupPrompt"
    // В 1.21.11 нужно найти аналог - возможно openWorld или другой метод
    
    // @ModifyVariable(method = "confirmWorldCreation", at = @At("HEAD"), ordinal = 0)
    // private static boolean startMixin(boolean original) {
    //     return false;
    // }

}
