package com.tecsun.jc.base.builder

import com.tecsun.jc.base.bean.db.invigilation.bean.StudentDetailsBean
import com.tecsun.jc.base.bean.db.invigilation.bean.TestingDetailsBean
import com.tecsun.jc.base.utils.DBFunctionUtil
import com.tecsun.jc.base.utils.log.LogUtil

/**
 * 考试房间处理
 * @author liudongwen
 * @date 2019/11/27
 */
object TestingRoomBuilder {
    fun saveAllRoom( list: HashSet<String>):Int{
        var totalSuccessSave = 0
        if(!list.isNullOrEmpty()){
            for(item in list){
               var count =  DBFunctionUtil.countTestingRoom(item?:"")
                if(count == 0){
                    var room = TestingDetailsBean()
                    room.schoolName = item?:""
                    var b = room.save()
                    if(b){
                        totalSuccessSave++
                        LogUtil.e(">>>>>>>>>>>>>>>>保存成功: ${room.schoolName}")
                    }
                }
            }
        }
        return totalSuccessSave
    }

    fun saveAllRoom( list: ArrayList<StudentDetailsBean>):Int{
        var totalSuccessSave = 0
        if(!list.isNullOrEmpty()){
            var roomHashSet = HashSet<String>()
            for(item in list){
                roomHashSet.add(item.schoolName?:"")
            }
            totalSuccessSave = saveAllRoom(roomHashSet)
        }
        return totalSuccessSave
    }

    fun getAllRoomsName():HashSet<String>{
        var list = DBFunctionUtil.findAllTestingRoom()
        var roomHashSet = HashSet<String>()
        for(item in list){
            roomHashSet.add(item.schoolName?:"")
        }
        return roomHashSet
    }

    fun getAllRooms():List<TestingDetailsBean>{
        return DBFunctionUtil.findAllTestingRoom()
    }

    fun countRoomByName(schoolName:String):Int{
        return DBFunctionUtil.countTestingRoom(schoolName?:"")
    }

    fun deleteRoom(schoolName:String){
        DBFunctionUtil.deleteTestingDetails(schoolName?:"")
    }


}
















