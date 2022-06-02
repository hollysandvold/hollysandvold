#!/bin/bash

LINES=$(wc -l $1|awk '{print $1}')

#reads a tweet file
#checks that num of lines is at least 10 000
if [[ "$LINES" -gt "9999" ]]
then
#print num of lines
	echo "$LINES" 	
#else, prints error message
else
#print error message
	echo 'Error: Your file has less than 10000 lines'
	exit 1
fi

#prints first file (header)
echo "$(head -n 1 $1)" 

#prints num of lines which contain "potus"
tail -n 10000 $1 | grep -ic "potus"

#prints of rows 100-200,how many contain the word "fake"
sed -n 100,200p $1 | grep -cw "fake"

exit 0