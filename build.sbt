
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

lazy val checkScalaStyle = taskKey[Unit]("checkScalaStyle")
checkScalaStyle := scalastyle.in(Test).dependsOn(scalastyle.in(Compile).toTask("")).toTask("").value

(test in Test) := ((test in Test) dependsOn checkScalaStyle).value

scalastyleFailOnWarning := true
scalastyleFailOnError := true
