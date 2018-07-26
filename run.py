import fastText

output = fastText.train_unsupervised("walk.txt", model='skipgram',lr = 0.005, dim=100, ws=15, epoch=20, minCount=0, minCountLabel=0, minn=0, maxn=0, neg=10, wordNgrams=1, loss='ns', bucket=2000000, thread=4, lrUpdateRate=100, t=0.0001, label='__label__', verbose=2, pretrainedVectors='')
words = output.get_words()
output.save_model("embeddings_weights.bin")
f = open("embeddings2_weights.txt","w+")
for word in words:
	v = output.get_word_vector(word)
	vstr = ""
	for vi in v:
		vstr += " " + str(vi)
	f.write(word + vstr+ "\n")
f.close()

