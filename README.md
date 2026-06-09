# Trabalho Prático: Resolução de Problemas de Fluxo Máximo, Corte Mínimo e Emparelhamento

## 📌 Nome do Problema
**Police Chase** (Perseguição Policial)  
🔗 [Link do Problema - CSES Task 1695](https://cses.fi/problemset/task/1695)

---

## 👥 Integrantes do Grupo
* Samuel Moreira
* Luis Guilherme
* Natan Adams
* José Guilherme

---

## 💻 Linguagem Utilizada
* **Linguagem:** Java
* **Versão recomendada:** OpenJDK 24

---

## 🚀 Como Executar a Solução

A nossa solução foi estruturada de forma modular em três arquivos para manter o código limpo e organizado. Certifique-se de que todos os arquivos (`Main.java`, `MaxFlowSolver.java`, `Edge.java`) estejam no mesmo diretório (`src/`).

1. Abra o terminal na pasta onde os arquivos estão localizados (`src/`).
2. Compile todas as classes simultaneamente:
   ```bash
   javac Main.java MaxFlowSolver.java Edge.java

Execute o programa através da classe principal:

Bash
java Main
Insira os dados de entrada manualmente ou redirecione um arquivo de texto de testes:

Bash
java Main < entradas_do_problema.txt
🗺️ Explicação da Modelagem como Rede de Fluxo
O problema consiste em encontrar o menor número de ruas que precisam ser fechadas para interceptar qualquer rota de fuga do ladrão entre o banco e o porto. Pelo teorema clássico Max-Flow Min-Cut (Fluxo Máximo = Corte Mínimo), o valor do fluxo máximo que cruza uma rede é exatamente igual à soma das capacidades das arestas que, se removidas, desconectam a rede de forma minimal.

Como o objetivo é minimizar a quantidade de ruas bloqueadas (e não uma distância ou peso), modelamos cada rua da cidade como um canal de transmissão com capacidade unitária (igual a 1).

Definições Estruturais da Rede:
Origem (Source): Vértice 1, representando o cruzamento onde o banco foi roubado.

Sorvedouro (Sink): Vértice n, representando o porto onde o ladrão tenta chegar.

Vértices (V): Os cruzamentos da cidade (variando de 2 a 500).

Arestas (E): As ruas que conectam os cruzamentos. Como o enunciado define que as ruas são de mão dupla (bidirecionais), cada rua gera duas arestas direcionadas na nossa estrutura de dados: uma de u para v e outra de v para u, ambas com capacidade inicial 1.

🧠 Algoritmo Utilizado e Justificativa
Optamos por utilizar o algoritmo de Edmonds-Karp.

Justificativa de Evolução: Durante o planejamento, avaliamos o método de Ford-Fulkerson puro (que comumente utiliza Busca em Profundidade - DFS). Embora as capacidades deste problema sejam unitárias (o que mitiga o pior caso clássico de Ford-Fulkerson com caminhos alternados infinitos), a DFS pode encontrar caminhos longos e tortuosos desnecessariamente antes de saturar a rede, além de correr o risco de estourar a pilha de recursão (StackOverflow) em grafos densos ou lineares.

A escolha do Edmonds-Karp introduz a Busca em Largura (BFS) para encontrar os caminhos aumentantes. Isso garante que o caminho escolhido a cada iteração seja sempre o mais curto em número de arestas. Essa estratégia traz uma previsibilidade matemática estrita de tempo de execução, ideal para os limites do juiz online CSES.

🔄 O Papel do Grafo Residual
O grafo residual permite o funcionamento dinâmico do algoritmo. Para cada aresta direta criada com capacidade 1, criamos uma aresta reversa correspondente com capacidade inicial 0.

Quando a BFS localiza um caminho aumentante e "empurra" o fluxo por ele, a capacidade residual da aresta direta diminui, enquanto a capacidade da aresta reversa aumenta na mesma proporção. Esse mecanismo permite o efeito de "arrependimento": em iterações posteriores, o fluxo pode ser enviado de volta por uma aresta reversa, desfazendo caminhos mal otimizados feitos anteriormente e garantindo que o fluxo global máximo seja atingido de forma justa.

✂️ Conversão do Fluxo em Resposta (Corte Mínimo)
O valor inteiro devolvido pelo método edmondsKarp nos dá o número mínimo de ruas a fechar (o fluxo máximo). Para listar quais são essas ruas, usamos o estado final do grafo residual:

Realizamos uma última busca (BFS) a partir do banco (nó 1), mas com uma condição estrita: só atravessamos arestas que possuam capacidade residual maior que zero.

Essa busca divide os nós do grafo em dois subconjuntos isolados:

Conjunto S: Nós que ainda são alcançáveis a partir do banco.

Conjunto T: Nós que se tornaram inacessíveis (onde se encontra o porto n).

Percorremos a lista de ruas originais fornecidas na entrada. Se uma rua conecta um nó pertencente a S com um nó pertencente a T (ou vice-versa), significa que a capacidade dessa rua foi completamente esgotada pelo fluxo máximo. Logo, ela faz parte da fronteira do corte mínimo e deve ser fechada pela polícia.

📊 Análise de Complexidade por Casos
Embora a complexidade teórica geral do Edmonds-Karp seja de O(V * E^2), a natureza do nosso problema (onde todas as capacidades são obrigatoriamente 1) otimiza drasticamente o comportamento do algoritmo no pior caso real:

Melhor Caso - O(E): Ocorre se o banco e o porto já estiverem desconectados desde o início (nenhuma rua os liga). O algoritmo roda uma única BFS, não encontra caminhos até o sorvedouro e encerra imediatamente. O custo é apenas o tempo de varredura da BFS, que é proporcional ao número de arestas O(E).

Pior Caso Geral (Teórico) - O(V * E^2): Seria o pior caso do algoritmo em redes genéricas com capacidades flutuantes e gigantescas, onde uma mesma aresta poderia ser saturada e desfeita repetidas vezes.

Pior Caso Real (Rede Unitária no CSES) - O(V * E): Como cada rua tem capacidade máxima igual a 1, o fluxo total da rede é limitado pelo número de vértices conectados à origem (Fluxo Máximo <= V). Como cada caminho aumentante adiciona obrigatoriamente 1 unidade de fluxo, a BFS será executada no máximo V vezes. Sendo o custo de cada BFS igual a O(E), o pior caso absoluto neste problema cai para O(V * E).

Caso Típico (Médio) - O(K * E): Onde K é o número real de caminhos aumentantes curtos até bloquear a rede. Em mapas urbanos normais, K assume valores pequenos, fazendo com que o algoritmo execute em poucos milissegundos (aproximadamente 2ms a 5ms para os limites do problema), consumindo uma fração mínima do limite de 1.00s.

⚠️ Casos Especiais Relevantes
Grafos Inicialmente Desconexos: Caso não haja rota original entre o banco e o porto, a primeira BFS falha, o loop quebra e o sistema retorna fluxo 0 (nenhuma rua precisa ser fechada), tratando o caso de forma nativa.

Rotas de Volta (Mão Dupla): Por se tratarem de ruas bidirecionais, o fluxo que vai de A para B compartilha o grafo residual com o fluxo de B para A. O controle do ponteiro reverse dentro do objeto Edge impede interferências incorretas e garante que o fluxo seja balanceado perfeitamente.
