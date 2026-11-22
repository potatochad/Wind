package com.productivity.wind.Imports.Utils.SaveData.List

//‼️ISSUES
//DEAL LATER WITH TYPE CHANGED OR NAME CHANGED in class
//only handles 3 types (bool, str, int)
//Currently the hole list updates on a tiny change.
//THERE IS A bunch of data saved data (that doesnt get cleaned up, after years, massive issue)
//It doesnt handle Delete or clear (saved doesnt change)
// CustomAlertList may habe way more inputs that needs do (like onAdd  but you know others)
//Doesnt save in order (of like index, completly ignores that), it doesnt onBuild in orderrr. not 1,2,3 indexes, but 5,1,3
import com.productivity.wind.Imports.Utils.VarVal.*
import com.productivity.wind.Imports.Utils.SaveData.*
import com.productivity.wind.Imports.Utils.Log.*
import com.productivity.wind.Imports.Utils.Generic_list.*
import com.productivity.wind.Imports.Utils.*
import com.productivity.wind.Imports.Utils.AppsAndDevice.*
import androidx.compose.runtime.*
import com.productivity.wind.*
import kotlin.collections.*


fun < T : EasySave> TrackList(
    createItem: () -> T,
    defaultItems: List<T> = emptyList()
): By<EasyList<T>> {
    var list = EasyList<T>()


    var times by mState(0)
    val save = IgnoreRepeatedCalls(1000, appNonUIScope) {
        list.each {
            if (it.changed) it.save()
        }
    }

    return By(list)
        .onBuild { prop, listName, mValue -> 
            val data = ListData(listName).all
            Vlog("listData: ${data}")
                

            val items = data.map { (id, _) ->
                createItem().apply {
                    this.id = id
                    this.listName = listName
                    this.onChanged = save::run
                }
            }
            
            list.addAll(items)
            
            list.onAdd { 
                it.changed = yes
                it.onChanged = save::run
                it.id = "$listName:${Id()}"
                it.listName = listName
                save.run() 
            }
            list.onAddAll { 
                Vlog("DONT USE onAddALL !!!")
                //‼️ its not implemented yet!!
            }
            list.onRemove{
                ListData(listName).remove(it.id)
            }
                
            mValue.it = list

            
        }
        .onSet { _, _, _ -> VlogOne("LIST MUST BE VAL") }
}

//nothing can be private
abstract class EasySave {
    var id = ""
    var listName = ""
    
    var changed = no
    var onChanged: Do = {}
    
    
    val vars = MapVarInfo()
    
    inline fun <reified T> lazyS(x: T): By<T> {
        var theBy = By(x)
            .onBuild{ prop, name, mValue -> 
                vars.add(name, mValue.it)
            }
            .onFirstGetOrSet{ prop, name, mValue -> 
                //DOESNT DO FOR WindErrors.UVST
                val savedValue = ListData(listName).getVarValue(id, name)
                
                if (savedValue != null) mValue.it = savedValue as T
                
                vars.add(name, mValue.it)
            }
            .onSet { prop, name, value ->
                vars.edit(name, value)
                
                changed = yes
                onChanged()
            }

        return theBy
    }

    //ADD A SLOW.RUNNER. THAT SLOWLY CHECKS IF ALL CHANGEDDD
    //AND HAVE IT BE LIKE IF WANT NOWW. TO GO FASTERR‼️‼️


    open fun save(){
        var varList = VarsList(vars)
        Vlog("varList: ${varList}")
        Vlog("listName: ${listName}")
        
        ListData(listName).put(id, varList) 
        changed = no
    }
}
    
    


class TestData : EasySave() {

    var name by lazyS("item")
    var enumThing by lazyS(WebAction.Allow)
}





