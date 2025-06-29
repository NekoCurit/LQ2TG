package net.nekocurit.lq2tg.config.configs

import kotlinx.serialization.Serializable
import java.io.File

/**
 * 对 OneBot 获取文件(/get_file)端点返回的位置进行转换
 * 适用于部分 OneBot 实现返回的是本地文件位置 而非 url 且该实现运行在容器下
 *
 * @param prefixRemove 要去除的前缀 例: /app/.config/QQ/NapCat/temp/
 * @param prefixAdd 要添加的前缀 例: /var/lib/docker/volumes/xxx/_data/NapCat/temp/
 * @param autoDelete 上传后是否自动删除本地
 */
@Serializable
class DataConfigForwardOneBotLocalFile(
    val prefixRemove: String = "",
    val prefixAdd: String = "",
    val autoDelete: Boolean = true
) {
    fun apply(file: String) = File("${prefixAdd}${file.removePrefix(prefixRemove)}")
}