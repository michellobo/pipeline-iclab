import groovy.util.logging.Log
def call(){
  
    pipeline {
        agent any
        parameters {
            choice(name: 'parametro', choices: ['maven', 'gradle'], description: 'maven o gradle')
            string(name: 'Stage(s)', defaultValue: '', description: 'stage(s)')
        }
        stages {
            stage('Pipeline') {
                steps {
                    script {
                        stage('ejecucion'){
                            stagesMaven =  ['1','2','3','4','5']
                            stagesGradle = ['1','2','3','4','5']
                            println "----------------------------------------";
                            println env.CHANGE_AUTHOR;
                            println env.BRANCH_NAME;
                            println "----------------------------------------";
                            env.PASO = env.STAGE_NAME;
                            String[] stageArray;
                            type = env.BRANCH_NAME ==~ /^(feature-)[-a-zA-Z0-9]+/ ? "feature" : "0"
                            type = env.BRANCH_NAME ==~ /^(release-v)[1-9]+\-[1-9]+\-[1-9]+/ ? "release" : "0"
                            type = env.BRANCH_NAME ==~ /(main|master)/ ? "master" : "0"
                            type = env.BRANCH_NAME ==~ /^(develop)/ ? "develop" : "0"
                            println "type: ----------------${type}------------------------";
                            switch(type) {
                                case "feature":
                                    
                                break
                                case "release":
                                    
                                break
                                case "develop":
                                    
                                break
                                case "master":
                                    env.ERROR = "NO SE PERMITE ACCIONES EN MASTER "
                                    println "------------------NO SE PERMITE ACCIONES EN MASTER----------------------";
                                break
                                default:
                                    env.ERROR = "Branch no valido "
                                    println "------------------Branch no valido----------------------";
                                break
                            }
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
                                    env.ERROR = env.ERROR + "No coinciden los stage(s): ${listError} "
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