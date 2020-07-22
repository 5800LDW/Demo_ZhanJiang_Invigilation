package com.tecsun.jc.base.widget.webview.x5

import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebView

abstract class BaseX5WebView : WebView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {}

    constructor(context: Context, attributeSet: AttributeSet, i: Int) : super(context, attributeSet, i) {}

    constructor(context: Context, attributeSet: AttributeSet, i: Int, b: Boolean) : super(context, attributeSet, i, b) {}

    constructor(
        context: Context,
        attributeSet: AttributeSet,
        i: Int,
        map: Map<String, Any>,
        b: Boolean
    ) : super(context, attributeSet, i, map, b) {
    }
}
