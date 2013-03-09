#! /bin/sh -x
while read p; do
filename=$(basename "$p")
extension="${filename##*.}"
filename="${filename%.*}"
echo $extension
if["$extension" -eq "java"]
then
 cat $p >> $extension.txt
fi
done < fileNames.sh
