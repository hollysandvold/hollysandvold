from os import write
import pandas as pd
import sys
import json

#read file and save output file
infile=sys.argv[3]
outfile=sys.argv[2]
df = pd.read_csv(infile)

#count number of times each pony speaks
twilight=0
apple=0
rarity=0
pinkie=0
rainbow=0
flutter=0
other=0

for x in df.index:
    ponyName = df.loc[x, 'pony']
    if(ponyName.casefold()=="Twilight Sparkle".casefold()):
        twilight=twilight+1
    elif (ponyName.casefold()=="Applejack".casefold()):
        apple=apple+1
    elif (ponyName.casefold()=="Rarity".casefold()):
        rarity=rarity+1
    elif (ponyName.casefold()=="Pinkie Pie".casefold()):
        pinkie=pinkie+1
    elif (ponyName.casefold()=="Rainbow Dash".casefold()):
        rainbow=rainbow+1
    elif (ponyName.casefold()=="Fluttershy".casefold()):
        flutter=flutter+1
    else:
        other=other+1
#calculate speaking percents
total=twilight+apple+rarity+pinkie+rainbow+flutter+other
twilightPercent=round(twilight/total,2)
applePercent=round(apple/total,2)
rarityPercent=round(rarity/total,2)
pinkiePercent=round(pinkie/total,2)
rainbowPercent=round(rainbow/total,2)
flutterPercent=round(flutter/total,2)

#put counts into output file
data= {
     "count": {
         "twilight sparkle": twilight,
        "applejack": apple,
        "rarity": rarity,
        "pinkie pie": pinkie,
        "rainbow dash": rainbow,
        "fluttershy": flutter
        },
    "verbosity": {
        "twilight sparkle": twilightPercent,
        "applejack": applePercent,
        "rarity": rarityPercent,
        "pinkie pie": pinkiePercent,
        "rainbow dash": rainbowPercent,
        "fluttershy": flutterPercent
    }
}

with open(outfile, "w") as write_file:
    json.dump(data, write_file, indent=4)