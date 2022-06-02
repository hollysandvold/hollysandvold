#!/bin/bash

if [[ -z $1 ]]
then
	echo "Error: No log file given."
	echo "Usage: ./webmetrics.sh <logfile>"
	exit 1
elif [[ ! -f $1 ]]
then
	echo "Error: File '$1' does not exist."
        echo "Usage: ./webmetrics.sh <logfile>"
	exit 2
fi

echo "Number of requests per web browser"
numReqSafari=$(grep -co "Safari" $1)
numReqFirefox=$(grep -co "Firefox" $1)
numReqChrome=$(grep -co "Chrome" $1) 
echo "Safari, $numReqSafari"
echo "Firefox, $numReqFirefox"
echo "Chrome, $numReqChrome"
echo ""i

echo "Number of distinct users per day"
dates=($(awk '{print $4}' $1 | cut -d'[' -f2 | cut -d':' -f1 | sort -u))
for num in "${dates[@]}"
do
	echo "$num", "$(grep $num $1 | awk '{print $1}' | sort -u | wc -l)"
done
echo ""

echo "Top 20 popular product requests"
grep -E 'GET /product/[0-9]{1,}/' $1 | awk '{print $7}' | cut -d'/' -f3 | sed '/model/d' | sort | uniq -c | sort -k1 -k2 -rn | head -n 20 | awk '{print $2 "," $1}' 
echo ""

exit 0