
lazy val phoneCompany = (project in file(".")).settings(
  Seq(
    name := "disco-test-phone-company",
    version := "1.0",
    scalaVersion := "2.12.12",
    maintainer := "tumakha@gmail.com",
    fork in run := true,
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % "3.0.0",
      "org.scalatest" %% "scalatest" % "3.2.0" % Test
    )
  )
).enablePlugins(JavaAppPackaging)
