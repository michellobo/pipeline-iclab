import groovy.util.logging.Log
def call(){
  
    pipeline {
        agent any
        parameters {
            string(name: 'Stage(s)', defaultValue: '', description: 'stage(s)')
            string(name: 'Branch', defaultValue: '', description: 'Branch (feature, develop o release)')
            choice(name: 'parametro', choices: ['maven', 'gradle'], description: 'abc')
            string(name: 'hola', defaultValue: '', description: 'hola')
        }
        stages {
            stage('Pipeline') {
                steps {
                    script {
                        stage('ejecucion'){
                            stagesMaven =  ['1','2','3','4','5']
                            stagesGradle = ['1','2','3','4','5']
                            println "----------------------------------------";
                            println env.BRANCH_NAME;
                            println "----------------------------------------";
                            env.PASO = env.STAGE_NAME;
                            String[] stageArray;
                            String type;
                            def patternFeature = "^feature-"
                            println (env.BRANCH_NAME =~ patternFeature)
                            /*if (assert env.BRANCH_NAME ==~ patternFeature) {
                                println "-----------------ENTRO-----------------------";
                                println (assert env.BRANCH_NAME ==~ patternFeature)
                            }*/
                            stageArray = params.stage.split(';');
                            if (params.parametro == 'gradle') {
                                result = stageArray - stagesGradle
                                println result.size()
                                println stageArray.size()
                                if(result.size() == 0 || (stageArray.size() == 1 && stageArray[0] == "")){
                                    gradle.call(stagesGradle, stageArray)
                                }else{
                                    String listError =  "[\"${result.join('", "')}\"]"
                                    println listError
                                    env.ERROR = "No coinciden los stage(s): ${listError}"
                                    assert result.size() == 0
                                }
                            }else{
                                result = stageArray - stagesMaven
                                println result.size()
                                println stageArray.size()
                                if(result.size() == 0 || (stageArray.size() == 1 && stageArray[0] == "")){
                                    maven.call(stagesMaven, stageArray)
                                }else{
                                    String listError =  "[\"${result.join('", "')}\"]"
                                    println listError
                                    env.ERROR = "No coinciden los stage(s): ${listError}"
                                    assert result.size() == 0
                                }
                            }
                        }
                    }
                }
            }
        }
        post {
            success {
                slackSend channel: 'U01DC6FDX70', color: 'good', message: "Build Success: [Michell Lobo][${env.JOB_NAME}][${params.parametro}] Ejecución exitosa.", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-michellobo'
            }
            failure {
                slackSend channel: 'U01DC6FDX70', color: 'danger', message: "Build Failure: [Michell Lobo][${env.JOB_NAME}][${params.parametro}] Ejecución fallida en stage [${env.PASO}] _ [${env.ERROR}]", teamDomain: 'dipdevopsusach2020', tokenCredentialId: 'slack-michellobo'
            }
        }
    }

}

return this;