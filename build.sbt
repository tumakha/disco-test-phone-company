import sbt.Keys.libraryDependencies

lazy val phoneCompany = (project in file(".")).settings(
  Seq(
    name := "disco-test-phone-company",
    version := "1.0",
    scalaVersion := "2.12.12",
    maintainer := "tumakha@gmail.com",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "3.0.0"
    )
  )
).enablePlugins(JavaAppPackaging)
