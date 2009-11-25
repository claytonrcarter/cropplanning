#!/bin/sh

#
# This script taken verbatim from the JarBundler ANT task package.  Some comments added.
# 12/10/08
#

# base location of the project of the SRC and DEST folders
BASE="$1"
# location of the directory to be turned into a disk image
SRC="$2"
# location of the directory where the files will be prepped and worked on
DEST="$3"
# base name of the created image (without extension)
VOLUME="$4"

echo Base Directory: `pwd`/$1
echo Source Dir: $2
echo Destination Dir: $3
echo Volume Name: $4

TEMP="TEMPORARY"

cd $BASE

if [[ -e $DEST/$VOLUME.dmg ]];
then
  echo Removing old disk image: $DEST/$VOLUME.dmg
  rm $DEST/$VOLUME.dmg
fi

# create 10MB image
hdiutil create -megabytes 25 $DEST/$TEMP.dmg -layout NONE
MY_DISK=`hdid -nomount $DEST/$TEMP.dmg`

# mount and unmount the image
newfs_hfs -v $VOLUME $MY_DISK
hdiutil eject $MY_DISK

# remount the image
hdid $DEST/$TEMP.dmg

# change file permissions of source files and copy source files onto image
# chflags -R nouchg,noschg "$SRC"
ditto -rsrc -v "$SRC" "/Volumes/$VOLUME"
# ditto -rsrcFork -v "./background/" "/Volumes/$VOLUME"

# eject volume convert and change settings of the image
hdiutil eject $MY_DISK
hdiutil convert -format UDCO $DEST/$TEMP.dmg -o $DEST/$VOLUME.dmg
hdiutil internet-enable -yes $DEST/$VOLUME.dmg

# clean up temp file
rm $DEST/$TEMP.dmg