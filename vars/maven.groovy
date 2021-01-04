def call(stagesMaven, stageArray){
    try{
        if ((stageArray.size() == 1 && stageArray[0] == "") || stageArray.contains(stagesMaven[0])){
                stage(stagesMaven[0]) { //1
                env.PASO = env.STAGE_NAME;
                println "1" 
            }
        }
        if ((stageArray.size() == 1 && stageArray[0] == "") || stageArray.contains(stagesMaven[1])){
            stage(stagesMaven[1]) { //2
                env.PASO = env.STAGE_NAME;
                println "2" 
            }
        }
        if ((stageArray.size() == 1 && stageArray[0] == "") || stageArray.contains(stagesMaven[2])){
            stage(stagesMaven[2]) { //3
                env.PASO = env.STAGE_NAME;
                println "3" 
            }
        }
        if ((stageArray.size() == 1 && stageArray[0] == "") || stageArray.contains(stagesMaven[3])){
            stage(stagesMaven[3]) { //4
                env.PASO = env.STAGE_NAME;
                println "4" 
            }
        }
        if ((stageArray.size() == 1 && stageArray[0] == "") || stageArray.contains(stagesMaven[4])){
            stage(stagesMaven[4]) { //5
                env.PASO = env.STAGE_NAME;
                println "5"
            }
        }
    } catch(Exception e){
        error('Ha ocurrido un error: ' + e)
    }
}

return this;