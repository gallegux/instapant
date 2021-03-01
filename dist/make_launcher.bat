@echo off


:EMPAQUETAR
set DIR_OUT=D:\ffgr33\owncloud\jars\instapant

echo ----- CREAR PAQUETES -----

jar cvf %DIR_OUT%\instapant-core.jar -C ..\bin .

echo ----- CREAR LANZADOR -----

set CP=D:\ffgr33\owncloud\jars\japon.jar
set PKG=gallegux.japon.utils
set LANZADOR=%DIR_OUT%\Instapant-l.jar
set JAPON_FILE=instapant.j1.txt


java  -cp  %CP%  %PKG%.MakeLauncher  instapant.j1.txt  %DIR_OUT%\Instapant-l.jar


echo ----- CREAR JAPON -----

java  -cp  %CP%  %PKG%.MakeJaponFile  instapant.j1.txt  instapant.j1.txt  instapant.j2.txt  %DIR_OUT%\instapant.japon  %DIR_OUT%


echo ----- COPIAR JARS -----


:FIN