package com.llj.architecturedemo.ui.model

import com.llj.component.service.vo.TrackerDo
import java.io.Serializable

/**
 * BabyBazaar.
 * describe:
 * author llj
 * date 2018/10/22
 */
data class BabyHomeModuleItemVo(val title: String? = null) : TrackerDo(),Serializable {

    val link: String? = null
    val desc: String? = null
    val img_url: String? = null

    val have_time: String? = null
    val start_time: String? = null
    val end_time: String? = null

    val list: List<BabyHomeModuleItemVo?>? = null


}
