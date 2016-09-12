# python /source_model/java-tokens/word2vec.py --train_data /source_model/java-tokens/java_word2vec_small.txt --save_path /source_model/word2vec_save --eval_data /source_model/java-tokens/eval.txt --learning_rate 0.1 --embedding_size 128 --min_count 100 --batch_size 128

from word2vec import Word2Vec, Options
import tensorflow as tf

options = Options()
options.train_data="/source_model/java-tokens/java_word2vec_small.txt"
options.save_path="/source_model/word2vec_save"
options.eval_data="/source_model/java-tokens/eval.txt"
options.learning_rate=0.1
options.emb_dim=128 
options.min_count=100 
options.batch_size=128

sess = tf.InteractiveSession()
model = Word2Vec(options, sess)
model.saver.restore(sess, "/source_model/word2vec_save/model.ckpt-9842290")

model.analogy("[", "]", "{")
model.analogy("List", "ArrayList", "Set")
model.analogy("int", "Integer", "long")
model.analogy("[", "]", "<")
model.nearby(["Set"])
model.nearby(["Exception"])

from sklearn.manifold import TSNE
import matplotlib.pyplot as plt
tsne = TSNE(perplexity=30, n_components=2, init='pca', n_iter=5000)
low_dim_embs = tsne.fit_transform(model._emb.eval()[:300,:])

def plot_with_labels(low_dim_embs, labels, filename='tsne.png'):
  assert low_dim_embs.shape[0] >= len(labels), "More labels than embeddings"
  plt.figure(figsize=(48, 48))  #in inches
  for i, label in enumerate(labels):
    x, y = low_dim_embs[i,:]
    plt.scatter(x, y)
    plt.annotate(label,
                 xy=(x, y),
                 xytext=(5, 2),
                 textcoords='offset points',
                 ha='right',
                 va='bottom')
    
  plt.savefig(filename)

plot_with_labels(low_dim_embs, model._options.vocab_words[:300])

