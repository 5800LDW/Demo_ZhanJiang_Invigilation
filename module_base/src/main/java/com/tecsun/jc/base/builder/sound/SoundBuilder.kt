package com.tecsun.jc.base.builder.sound

import android.media.AudioManager
import android.media.SoundPool

import com.tecsun.jc.base.JinLinApp
import com.tecsun.jc.base.R
import com.tecsun.jc.base.utils.log.LogUtil

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.PriorityBlockingQueue


/**
 * 播放声音
 * @author liudongwen
 * @date 2019/11/05
 */
object SoundBuilder {
    private val TAG = "SoundBuilder"
    private var queue: PriorityBlockingQueue<PriorityEntity>? = null
    private var executor: ExecutorService? = null
    private var singleThreadExecutor: ExecutorService? = null
    private var allSoundPool: SoundPool? = null
    private var id_sound_sound_discern_success: Int? = null
    private var id_sound_sound_compare_success: Int? = null
    private var lineUp = 0

    init {
        myInit()
    }

    fun myInit(){
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>> 初始化 SoundBuilder2")
        initExecutorSoundUtil()
    }

    fun playCompareSuccess() {
        lineUp += 1
        put(R.raw.sound_compare_success, lineUp)
    }


    fun playDiscurnSuccess() {
//        playMySound(R.raw.sound_discern_success)
        lineUp += 1
        put(R.raw.sound_discern_success, lineUp)
    }

    private fun initExecutorSoundUtil() {
        if(queue == null){
            queue = PriorityBlockingQueue()
            executor = Executors.newSingleThreadExecutor()
            singleThreadExecutor = Executors.newSingleThreadExecutor()
            initAllSoundPool()
        }

    }


    private fun put(soundID: Int, lineUp: Int) {
        executor!!.execute {
            val priorityEntity = PriorityEntity()
            priorityEntity.soundID = soundID
            priorityEntity.lineUp = lineUp
            queue!!.put(priorityEntity)

            playSound()
        }
    }

    private fun playSound() {
        singleThreadExecutor!!.execute {
            try {
                if (queue!!.size > 0) {
                    val soundID = queue?.take()?.soundID ?: 0
                    soundNow(soundID)
                    //根据业务逻辑,只播放一次, 所以这里做了清空操作
//                    queue?.clear()
                }
            } catch (e: Exception) {
                LogUtil.e("SoundBuilder2  =$e")
            }
        }
    }

    private fun initAllSoundPool() {
        allSoundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        id_sound_sound_discern_success =
            allSoundPool?.load(JinLinApp.context, R.raw.sound_discern_success, 1)
        id_sound_sound_compare_success =
            allSoundPool?.load(JinLinApp.context, R.raw.sound_compare_success, 1)
    }

    private fun playMySound(loadId: Int?) {
        loadId.let {
            allSoundPool!!.play(loadId!!, 1f, 1f, 0, 0, 1.0f)
        }
    }

    private fun soundNow(rawId: Int) {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>> soundNow  rawId=$rawId")

        if (rawId == R.raw.sound_discern_success) {
            playMySound(id_sound_sound_discern_success)

        } else if (rawId == R.raw.sound_compare_success) {
            playMySound(id_sound_sound_compare_success)
        }
        else{
            LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>> 查无此soundId")
        }
    }
}

internal class PriorityEntity : Comparable<PriorityEntity> {
    var lineUp: Int = 0
    var soundID: Int = 0
    override fun toString(): String {
        return "PriorityEntity{" +
                "lineUp=" + lineUp +
                ", soundID=" + soundID +
                '}'.toString()
    }

    override fun compareTo(o: PriorityEntity): Int {
        return if (this.lineUp > o.lineUp) 1 else if (this.lineUp < o.lineUp) -1 else 0
    }
}