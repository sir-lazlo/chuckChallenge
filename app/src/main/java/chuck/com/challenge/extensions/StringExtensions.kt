package chuck.com.challenge.extensions

import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY

fun String.fromHtml() = HtmlCompat.fromHtml(this, FROM_HTML_MODE_LEGACY)
