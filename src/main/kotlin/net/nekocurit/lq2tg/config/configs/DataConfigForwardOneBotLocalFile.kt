package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable
import java.io.File

/**
 * 对 OneBot 获取文件(/get_file)端点返回的位置进行转换
 * 适用于部分 OneBot 实现返回的是本地文件位置 而非 url 且该实现运行在容器下
 *
 * @param remoteDir 服务端实现的现在目录 (服务端可以访问的) 例: /app/.config/QQ/NapCat/temp/
 * @param localDir 实际上它的下载目录 (LQ2TG可以访问的) 例: /var/lib/docker/volumes/xxx/_data/NapCat/temp/
 * @param autoDelete 上传后是否自动删除本地
 */
@Serializable
class DataConfigForwardOneBotLocalFile(
    val remoteDir: String = "",
    val localDir: String = "",
    val autoDelete: Boolean = true
) {
    fun apply(file: String) = File("${localDir}${file.removePrefix(remoteDir)}")
    fun applyToWrapper(file: File) = "${remoteDir}${file.absolutePath.removePrefix(localDir)}"
}