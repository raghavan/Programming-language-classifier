while read p; do
  cat $p >> ${p#*.}.txt
done < fileNames.txt
