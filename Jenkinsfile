#!groovy

node {
   def mvnHome
   stage('\u27A1 Preparation') {
      git 'git@github.com:openwms/shop.together.backend.git'
      mvnHome = tool 'M3'
   }
   stage('\u27A1 Build') {
      sh "'${mvnHome}/bin/mvn' clean install -Dbnumber=${BUILD_NUMBER} -Dbdate=${BUILD_ID}"
   }
   stage('\u27A1 Sonar') {
      sh "'${mvnHome}/bin/mvn' org.jacoco:jacoco-maven-plugin:prepare-agent sonar:sonar -Djacoco.propertyName=jacocoArgLine -Dbuild.number=${BUILD_NUMBER} -Dbuild.date=${BUILD_ID} -Pjenkins"
   }
   stage('\u27A1 Results') {
      archive 'target/*.jar'
   }
   stage('\u27A1 Heroku Staging') {
      sh "git remote add heroku https://:${HEROKU_API_KEY}@git.heroku.com/shop.together.backend.git"
      sh "git push heroku master -f"
   }
}