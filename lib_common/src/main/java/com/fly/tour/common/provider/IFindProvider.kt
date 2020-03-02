package com.fly.tour.common.provider


import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * Description: <IFindProvider><br>
 * Author:      mxdl<br>
 * Date:        2019/5/23<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
</IFindProvider> */
interface IFindProvider : IProvider {
    val mainFindFragment: Fragment
}
