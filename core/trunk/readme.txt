Arara

Load file in eclipse Help->software updates->Find and install-> search new features -> import

Windows->Open pespective->others-> SVN
new repository location
https://<user>@svn.berlios.de/svnroot/repos/arara

in directory core -> checkout as project

http://maven.apache.org/guides/mini/guide-ide-eclipse.html
mvn -Declipse.workspace=<path-to-eclipse-workspace> eclipse:add-maven-repo 

mvn clean
mvn -Dwtpversion=1.0 eclipse:eclipse
(mvn eclipse:eclipse)

mvn compile
mvn package

mvn site
mvn site:deploy

mvn install:install-file -DgroupId=javax.activation -DartifactId=activation -Dversion=1.0.2 -Dpackaging=jar -Dfile=activation.jar

mvn install:install-file -DgroupId=javax.mail -DartifactId=mail -Dversion=1.3.3 -Dpackaging=jar -Dfile=mail.jar

--------------------------
GoogleMaps
URL http://www.aves.brasil.nom.br/
KEY ABQIAAAAsGlxfJNNFT2UMdPvkWu4chRm87X1HRhpamZHTilSRw2Sr65wTBSt0eQyTHNf1DmOtbIoq1CNnvzFuA



