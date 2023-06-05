

Colocar en c/windows/system32:
[CLIPSJNI.dll](lib%2FCLIPSJNI.dll)

Ejecutar con git:
mvn install:install-file -Dfile=lib/CLIPSJNI.jar -DgroupId=net.sf.clipsrules -DartifactId=clipsjni -Dversion=6.4 -Dpackaging=jar
