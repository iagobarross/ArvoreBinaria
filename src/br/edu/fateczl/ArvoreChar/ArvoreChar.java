package br.edu.fateczl.ArvoreChar;

public class ArvoreChar {

	No raiz;

	public ArvoreChar() {
		raiz = null;
	}

	private void insertLeaf(No no, No raizSubArvore) {
		if (raiz == null) {
			raiz = no;
		} else if (no.dado < raizSubArvore.dado) {
			if (raizSubArvore.esquerda == null) {
				raizSubArvore.esquerda = no;
			} else {
				insertLeaf(no, raizSubArvore.esquerda);
			}
		} else if (no.dado > raizSubArvore.dado) {
			if (raizSubArvore.direita == null) {
				raizSubArvore.direita = no;
			} else {
				insertLeaf(no, raizSubArvore.direita);
			}
		}
	}

	public void insert(char dado) {
		dado = Character.toUpperCase(dado);
		
		No no = new No();
		no.dado = dado;
		no.direita = null;
		no.esquerda = null;
		insertLeaf(no, raiz);
	}

	private void prefix(No raizSubArvore) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore Vazia");
		} else {
			System.out.print(raizSubArvore.dado + " ");
			if (raizSubArvore.esquerda != null) {
				prefix(raizSubArvore.esquerda);
			}
			if (raizSubArvore.direita != null) {
				prefix(raizSubArvore.direita);
			}
		}
	}

	private void infix(No raizSubArvore) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore Vazia");
		} else {
			if (raizSubArvore.esquerda != null) {
				infix(raizSubArvore.esquerda);
			}
			System.out.print(raizSubArvore.dado + " ");
			if (raizSubArvore.direita != null) {
				infix(raizSubArvore.direita);
			}
		}
	}

	private void postfix(No raizSubArvore) throws Exception {
		if (raiz == null) {
			throw new Exception("Arvore Vazia");
		} else {
			if (raizSubArvore.esquerda != null) {
				postfix(raizSubArvore.esquerda);
			}
			if (raizSubArvore.direita != null) {
				postfix(raizSubArvore.direita);
			}
			System.out.print(raizSubArvore.dado + " ");
		}
	}

	public void prefixSearch() throws Exception {
		prefix(raiz);
	}

	public void infixSearch() throws Exception {
		infix(raiz);
	}

	public void postfixSearch() throws Exception {
		postfix(raiz);
	}

	private No nodeSearch(No raizSubArvore, char valor) throws Exception {
		valor = Character.toUpperCase(valor);
		if (raiz == null) {
			throw new Exception("Arvore Vazia");
		} else if (valor < raizSubArvore.dado) {
			return nodeSearch(raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return nodeSearch(raizSubArvore.direita, valor);
		} else {
			return raizSubArvore;
		}
	}

	private int nodeLevel(No raizSubArvore, char valor) throws Exception {
		valor = Character.toUpperCase(valor);
		if (raiz == null) {
			throw new Exception("Arvore Vazia");
		} else if (valor < raizSubArvore.dado) {
			return 1 + nodeLevel(raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return 1 + nodeLevel(raizSubArvore.direita, valor);
		} else {
			return 0;
		}
	}

	public void search(char valor) {
		valor = Character.toUpperCase(valor);
		try {
			No no = nodeSearch(raiz, valor);
			int level = nodeLevel(raiz, valor);
			System.out.println("Dado : " + no.dado + " no nível: " + level);
		} catch (Exception e) {
			System.err.println("Dado não encontrado");
		}
	}

	public boolean exists(char valor) {
		valor = Character.toUpperCase(valor);
		try {
			nodeSearch(raiz, valor);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void remove(char valor) throws Exception {
		valor = Character.toUpperCase(valor);
		try {
			removeChild(raiz, valor);
		} catch (Exception e) {
			throw new Exception("Valor inexistente");
		}
	}

	private void removeChild(No raizSubArvore, char valor) throws Exception {
		valor = Character.toUpperCase(valor);
		if (exists(valor)) {
			No no = nodeSearch(raizSubArvore, valor);
			No pai = nodeParent(null, raiz, no.dado);
			if (no.esquerda != null && no.direita != null) {
				No noTroca = no.esquerda;
				while (noTroca.direita != null) {
					noTroca = noTroca.direita;
				}
				pai = nodeParent(null, raiz, noTroca.dado);
				no.dado = noTroca.dado;
				removeOneOrZeroLeaf(pai, noTroca);
			} else {
				removeOneOrZeroLeaf(pai, no);
			}
		} else {
			throw new Exception("Valor inexistente");
		}
	}

	private No nodeParent(No parent, No raizSubArvore, char valor) throws Exception {
		valor = Character.toUpperCase(valor);
		if (raiz == null) {
			throw new Exception("Arvore Vazia");
		} else if (valor < raizSubArvore.dado) {
			return nodeParent(raizSubArvore, raizSubArvore.esquerda, valor);
		} else if (valor > raizSubArvore.dado) {
			return nodeParent(raizSubArvore, raizSubArvore.direita, valor);
		} else {
			return parent;
		}
	}

	private void change(No parent, No no, No novoNo) {
		if (parent.esquerda != null && parent.esquerda.dado == no.dado) {
			parent.esquerda = novoNo;
		} else if (parent.direita.dado == no.dado) {
			parent.direita = novoNo;
		}
	}

	private void removeOneOrZeroLeaf(No parent, No no) {
		if (no.esquerda == null && no.direita == null) {
			if (parent == null) {
				raiz = null;
			} else {
				change(parent, no, null);
			}
		} else if (no.direita != null) {
			if (parent == null) {
				raiz = no.direita;
			} else {
				change(parent, no, no.direita);
			}
		} else if (no.direita == null) {
			if (parent == null) {
				raiz = no.esquerda;
			} else {
				change(parent, no, no.esquerda);
			}
		}
	}
}
