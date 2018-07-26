from scipy import spatial
import simplejson
import json
import requests

# G is embedding of nodes.
f = open("graph.txt","r")
G = simplejson.load(f)
f.close()
#print(G)

# G2 is int to uid mapping
G2 = {}	
f = open("node_id_mapping.txt","r")
G2 = simplejson.load(f)
f.close()

#G3 is the original graph.
G3 = {}
f = open("original_graph.txt","r")
G3= simplejson.load(f)
f.close()

option = input("What do you want original post or recommendaions 0 or 1? \n")
while int(option) != -1:

	if (int(option)==0):
		i = input("Enter the Id")
		choice = input("user_creator or post_id")

		post_ids = []
		print(G3[i])
		for l in G3[i]:
			if G1[l] == choice:
				post_ids.append(G2[l])
		#print(G2[1185856])

		# url = 'http://10.9.51.33:8081/get_post_html'
		# payload = json.dumps({"posts" : post_ids, "page_name" : "post_sim",'j_username': 'amanr','j_password': 'palacio10'})
		# headers = {'content-type': 'application/json'}
		# r = requests.post(url, data=payload, headers=headers)
		# print(r.text)
	else :
		node = input("Enter the node")
		choice = input("Enter what you want to recommend")
		number = input("enter number of recommendaions that you want")
		post_ids = []
		creator = ""
		if G1[node] == "post_id":
			for l in G3:
				if G1[l]=="user_creator" and node in G3[l]:
					creator = l
					break
			print(user_creator)
			similarity = {}
			for i in G:
				if G1[i] == choice:
					similarity[i] = 1-spatial.distance.cosine(G[node], G[i])

			i = 0
			while i<int(number):
				sim_value = -1000
				sim_node = "-100000000"
				for j in similarity:
					if(similarity[j]>sim_value and j != node):
						sim_value = similarity[j]
						sim_node = j
				if sim_node not in G3[creator]:
					print(G2[sim_node])
					post_ids.append(G2[sim_node])
				else :
					i-=1
				del similarity[sim_node]
				i+=1;
		else :
			similarity = {}
			for i in G:
				if i!= "</s>" and G1[i] == choice:
					similarity[i] = 1-spatial.distance.cosine(G[node], G[i])


			post_ids = []
			i = 0
			while i<int(number):
				sim_value = -1000
				sim_node = "-100000000"
				for j in similarity:
					if(similarity[j]>sim_value and j != node):
						sim_value = similarity[j]
						sim_node = j
				if sim_node not in G3[node]:
					print(G2[sim_node])
					post_ids.append(G2[sim_node])
				else :
					i-=1
				del similarity[sim_node]
				i+=1;
		print(G2[node])

		# url = 'http://10.9.51.33:8081/get_post_html'
		# payload = json.dumps({"posts" : post_ids, "page_name" : "post_sim",'j_username': 'amanr','j_password': 'palacio10'})
		# headers = {'content-type': 'application/json'}
		# r = requests.post(url, data=payload, headers=headers)
		# print(r.text)
	option = input("What do you want original post or recommendaions 0 or 1? \n")
