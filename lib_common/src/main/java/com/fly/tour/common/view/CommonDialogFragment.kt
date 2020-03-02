package com.fly.tour.common.view

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import com.fly.tour.common.R
import com.fly.tour.common.util.DisplayUtil
import com.fly.tour.common.util.log.KLog

class CommonDialogFragment : androidx.fragment.app.DialogFragment() {
    private var mOnDialogClickListener: OnDialogClickListener? = null
    override fun dismiss() {
        super.dismiss()
        isShowing = false
        KLog.v(TAG, "dismiss start...")
    }
    override fun show(manager: FragmentManager, tag: String?) {
        if (manager != null) {
            super.show(manager, tag)
        }
        isShowing = true
    }

    fun setOnDialogClickListener(onDialogClickListener: OnDialogClickListener): CommonDialogFragment {
        mOnDialogClickListener = onDialogClickListener
        return this
    }

    interface OnDialogClickListener {
        fun onLeftBtnClick(view: View)
        fun onRightBtnClick(view: View)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(resources.displayMetrics.widthPixels - DisplayUtil.dip2px(40f) * 2, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_common_dialog, container, false)
        val arguments = arguments
        var title: String? = null
        var describe: String? = null
        var leftbtn: String? = null
        var rightbtn: String? = null
        var rightBtnTextColor = 0
        if (arguments != null) {
            title = arguments.getString("title")
            describe = arguments.getString("describe")
            leftbtn = arguments.getString("leftbtn")
            rightbtn = arguments.getString("rightbtn")
            rightBtnTextColor = arguments.getInt("rightbtncolor", 0)
        }
        val txtTitle = view.findViewById<TextView>(R.id.txt_common_dialog_title)
        val txtDescribe = view.findViewById<TextView>(R.id.txt_common_dialog_describe)
        val btnLeft = view.findViewById<Button>(R.id.btn_common_dialog_left)
        val btnRight = view.findViewById<Button>(R.id.btn_common_dialog_right)
        val btnHalving = view.findViewById<View>(R.id.view_halving_line)
        if (!TextUtils.isEmpty(title)) {
            txtTitle.text = title
        } else {
            txtTitle.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(describe)) {
            txtDescribe.text = describe
        }
        if (!TextUtils.isEmpty(leftbtn)) {
            btnHalving.visibility = View.VISIBLE
            btnLeft.visibility = View.VISIBLE
            btnLeft.text = leftbtn
        } else {
            btnHalving.visibility = View.GONE
            btnLeft.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(rightbtn)) {
            btnRight.text = rightbtn
        }
        if (rightBtnTextColor != 0) {
            btnRight.setTextColor(rightBtnTextColor)
        }
        btnLeft.setOnClickListener { view ->
            if (mOnDialogClickListener != null) {
                mOnDialogClickListener!!.onLeftBtnClick(view)
            }
            dismiss()
        }
        btnRight.setOnClickListener { view ->
            if (mOnDialogClickListener != null) {
                mOnDialogClickListener!!.onRightBtnClick(view)
            }
            dismiss()
        }
        return view
    }

    class Builder {
        internal lateinit var title: String
        internal lateinit var describe: String
        internal lateinit var leftbtn: String
        internal lateinit var rightbtn: String
        internal var btnRightTextColor: Int = 0
        internal lateinit var mListener: OnDialogClickListener


        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setDescribe(describe: String): Builder {
            this.describe = describe
            return this
        }

        fun setLeftbtn(leftbtn: String): Builder {
            this.leftbtn = leftbtn
            return this
        }

        fun setRightbtn(rightbtn: String): Builder {
            this.rightbtn = rightbtn
            return this
        }

        fun setOnDialogClickListener(listener: OnDialogClickListener): Builder {
            this.mListener = listener
            return this
        }

        fun setRightbtnTextColor(rightBtnTextcolor: Int): Builder {
            this.btnRightTextColor = rightBtnTextcolor
            return this
        }

        fun build(): CommonDialogFragment {
            return CommonDialogFragment.newInstance(this)
        }
    }

   override fun onCancel(dialog: DialogInterface) {
        if (dialog != null) {
            super.onCancel(dialog)
        }
        isShowing = false
        KLog.v(TAG, "onCancel start...")
    }

    companion object {
        val TAG = CommonDialogFragment::class.java!!.getSimpleName()
        var isShowing = false
            private set//避免弹多个dialog

        fun newInstance(builder: Builder): CommonDialogFragment {
            val commonDialogFragment = CommonDialogFragment()
            val args = Bundle()
            args.putString("title", builder.title)
            args.putString("describe", builder.describe)
            args.putString("leftbtn", builder.leftbtn)
            args.putString("rightbtn", builder.rightbtn)
            args.putInt("rightbtncolor", builder.btnRightTextColor)
            commonDialogFragment.mOnDialogClickListener = builder.mListener
            commonDialogFragment.arguments = args
            return commonDialogFragment
        }
    }
}
