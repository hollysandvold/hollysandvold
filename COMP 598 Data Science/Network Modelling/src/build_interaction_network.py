#python build_interaction_network.py -i /path/to/<script_input.csv> -o /path/to/<interaction_network.json>

#compute a network of who speaks to one another, undirected (no vector, just a link)
#pony speaks to whoever comes directly after them
#character cannot talk to itself 
#consider the top 101 most frequent characters (not just the 6 ponies
# Don’t include characters that contain the following words in their names: “others”, “ponies”, "and", "all"
# respect episode boundaries (change of episode ends an interaction)
#print all in lower case

import argparse
import pandas as pd
import json

def get_top_ponies(df):
    ponies = df.pony.value_counts().index.tolist()
    topPonies = []
    j = 0
    for pony in ponies:
        if j>100:
            break

        splitpony = pony.split()
        #print(splitpony)

        if not "others" in splitpony and not "all" in splitpony and not "ponies" in splitpony and not "and" in splitpony:
            topPonies.append(pony)
            j = j+1
    return topPonies

def update_score(charone, chartwo, interactions):
    if charone in interactions:
        if chartwo in interactions[charone]:
            interactions[charone][chartwo] = interactions[charone][chartwo] + 1
        else:
            interactions[charone][chartwo] = 1
    else:
        interactions[charone]= {chartwo: 1}
    return interactions

def main():
    # get arguments 
    parser = argparse.ArgumentParser()
    parser.add_argument("-o")
    parser.add_argument("-i")
    args = parser.parse_args()
    
    output = args.o
    infile = args.i

    df = pd.read_csv(infile)
    df = df.drop(columns=['writer', 'dialog'])
    df['pony'] = df['pony'].str.lower()
    
    #get top 101 ponies
    topPonies = get_top_ponies(df)
    #print(topPonies)
    interactions = {}
    
    for i in df.index:
        if i == len(df.index)-1:
            break

        #catch ponies that have already been removed
        try:
            currpony = df['pony'][i]
        except KeyError:
            continue

        nextpony = df['pony'][i+1]

        #check if they're just talking to themselves
        if currpony == nextpony:
            continue

        if currpony not in topPonies:
            continue

        if nextpony not in topPonies:
            #df = df.drop(index=i+1)
            continue


        #make sure that they're in the same same episode 
        if df['title'][i] == df['title'][i+1]:
            #update curr pony
            interactions = update_score(currpony, nextpony, interactions)
            #update next pony
            interactions = update_score(nextpony,currpony, interactions)

    with open(output, 'w') as f:
        json.dump(interactions, f, indent=4)

if __name__ == '__main__':
    main()
