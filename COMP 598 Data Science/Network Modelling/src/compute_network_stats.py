#python compute_network_stats.py -i /path/to/<interaction_network.json> -o /path/to/<stats.json>
import argparse
import networkx as nx
import json

from networkx.algorithms.centrality.group import prominent_group

def main():
    # get arguments 
    parser = argparse.ArgumentParser()
    parser.add_argument("-o")
    parser.add_argument("-i")
    args = parser.parse_args()
    
    output = args.o
    infile = args.i

    #put all the json file data into a network
    G = nx.Graph()
    
    with open(infile) as f:
        data = json.load(f)

    numOfEdges = {}
    weightOfEdges = {}

    # create the network
    for pony1 in data:
        weightTotal = 0
        for pony2 in data[pony1]:
            weightTotal = weightTotal+data[pony1][pony2]
            if G.has_edge(pony1, pony2):
                continue
            else:
                G.add_edge(pony1, pony2, weight=data[pony1][pony2])
        numOfEdges[pony1] = len(G.edges(pony1))
        weightOfEdges[pony1] = weightTotal
    
    # get the sorted lists 
    x = nx.betweenness_centrality(G, weight=None, normalized=True)
    betweenness = dict(sorted(x.items(), key=lambda item: item[1], reverse = True))
    numOfEdges = sorted(numOfEdges.items(), key=lambda item: item[1], reverse=True)
    weightOfEdges = sorted(weightOfEdges.items(), key=lambda item: item[1], reverse=True)

    # The top three most connected characters by # of edges.
    # The top three most connected characters by sum of the weight of edges.
    # The top three most central characters by betweenness.
    topPonies = {'most_connected_by_num': [], 'most_connected_by_weight':[],'most_central_by_betweenness':[]}
    i = 0
    while i < 3:
        topPonies['most_connected_by_num'].append(numOfEdges[i][0])
        i= i +1
    i = 0
    while i < 3:
        topPonies['most_connected_by_weight'].append(weightOfEdges[i][0])
        i= i +1
    
    i = 0
    for pony in betweenness:
        if i > 2:
            break
        topPonies['most_central_by_betweenness'].append(pony)
        i= i +1
    
    #write to output
    with open(output, 'w') as f:
        json.dump(topPonies, f, indent = 4)


if __name__ == '__main__':
    main()
