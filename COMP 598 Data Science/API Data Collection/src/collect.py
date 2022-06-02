# collects 100 posts from each of the following subreddits: 
# Sample 1: most subscribed 
# funny, AskReddit, gaming, aww, pics, Music, science, worldnews, videos, todayilearned
# Sample 2: most # of posts 
# AskReddit, memes, politics, nfl, nba, wallstreetbets, teenagers, PublicFreakout, leagueoflegends, unpopularopinion
# saves output in sample1.json and sample2.json, respectively, saving one dict per line

import os
import os.path
import requests
import json
BASE_URL = "https://oauth.reddit.com/r/"
OUTPUT1 = "sample1.json"
OUTPUT2 = "sample2.json"
SECRET_TOKEN = "8DSE8qzU6k-Zm9ETNrCfALFXVm_Epg"
CLIENT_ID = 'SKnhlZ_9lSvcd9j5MnV_Bg'

def setup():
    auth = requests.auth.HTTPBasicAuth(CLIENT_ID, SECRET_TOKEN)
        
    # here we pass our login method (password), username, and password
    data = {'grant_type': 'password',
            'username': 'HollySand',
            'password': 'Reid2002!'}

    # setup our header info, which gives reddit a brief description of our app
    headers = {'User-Agent': 'Holly/0.0.1'}

    # send our request for an OAuth token
    res = requests.post('https://www.reddit.com/api/v1/access_token',auth=auth, data=data, headers=headers)
    
    # convert response to JSON and pull access_token value
    TOKEN = res.json()['access_token']

    # add authorization to our headers dictionary
    headers = {**headers, **{'Authorization': f"bearer {TOKEN}"}}

    return headers

def create_output(subreddits, headers, OUTPUT):
    start = 1
    for subreddit in subreddits:
        res = requests.get("https://oauth.reddit.com/r/%s/new.json?limit=100" % subreddit, headers=headers)
        if start == 1:
            with open(OUTPUT, 'a') as f:
                data = res.json()
                start2 = 1
                for child in data["data"]["children"]:
                    if start2 == 1:
                        json.dump(child, f)
                        start2 = 0
                    else:
                        f.write("\n")
                        json.dump(child,f)
            start = 0
        else:
            with open(OUTPUT, 'a') as f:
                f.write("\n")
                data = res.json()
                start2 = 1
                for child in data["data"]["children"]:
                    if start2 == 1:
                        json.dump(child, f)
                        start2 = 0
                    else:
                        f.write("\n")
                        json.dump(child,f)
        f.close()

def main():
    headers = setup()
    
    if os.path.exists(OUTPUT1):
        os.remove(OUTPUT1)

    if os.path.exists(OUTPUT2):
        os.remove(OUTPUT2)
    
    subreddits1 = ["funny", "AskReddit", "gaming", "aww", "pics", "Music", "science", "worldnews", "videos", "todayilearned"]
    subreddits2 = ["AskReddit", "memes", "politics", "nfl", "nba", "wallstreetbets", "teenagers", "PublicFreakout", "leagueoflegends", "unpopularopinion"]
    
    create_output(subreddits1, headers, OUTPUT1)
    create_output(subreddits2, headers, OUTPUT2)

    exit()


if __name__ == '__main__':
    main()
