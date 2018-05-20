## Introdução
As soluções para os problemas de concurrência offline dividem-se em dois modelos principais: Optimistic Offline Lock e Pessimistic Offline Lock.
Na solução pessimista o lock é adquirido no início da transação de negócio antes de usar os dados. Consequentemente é apenas permitida a uma transação de cada vez ter acesso aos dados, mas com a vantagem de garantir a persistência dos dados alterados, porque se garante que não existem duas transações a trabalhar os mesmos dados ao mesmo tempo.

Na solução otimista os conflitos sao detetados imediatamente antes do commit, fazendo roll-back da transação, e garantindo que as alterações a ser persistidas por uma sessão não entram em conflito com a de outra sessão.

A Fénix Framework utiliza uma abordagem Otimista e ainda implementa outros mecanismos, nomeadamente, Coarse-Grained Lock e Implicit Lock. Estes mecanismos permitem adquirir o lock vários objetos relacionados, sem ter que os adquirir individualmente, e fatorizar a codificação dos locks de modo a que os mecanismos de controlo de concorrência sejam implícitos à framework.


## 100 writes
Para a execução do teste criamos um activity provider que vai possuir 100 actividades criadas, um bank com todos os clientes do sistema (activity provider, hotel, RentACar, 100 brokers e 1 cliente) um hotel com os vários quartos disponiveis, um RentACar com todos os cars, um broker com várias adventures constituidas pelos vários elementos criados anteriormente e de seguida colocou-se o cliente a fazer 100 process adventures concorrentes.

Como a fénix-framework segue uma politica optimista, de coarse-grained lock, ao fazer os 100 writes simultaneamente são gerados vários conflitos quando as transações tentam fazer commit para a base de dados e é verificado que mais do que um utilizadores alterou a mesma informação. Verificou-se que todos os process adventures falhados ocorrem no inicio do teste, que se pode explicar pelo grande número de acessos concorrentes ao inicio do teste, 100 utilizadores diferentes, ocorrendo uma diminuição progressiva dos erros ocorridos que acompanha uma diminuição progressiva dos utilizadores activos no sistema, pois existe menos probabilidade de vários utilizadores alterarem a mesma informação, podendo gerar um erro. Estes aspectos traduziram-se numa taxa de erro de 39,9% o que é um valor bastante elevado.

Observou-se um desvio padrão na execução dos process adventures de 10847.82 milisegundos, onde um process adventures pode se executar no pior caso em 57108 milisegundos, num sistema sobrelotado (com 100 utilizadores) ou no melhor caso em 33 milisegundos num sistema a trabalhar dentro do seu intervalo de confiança, o que se traduz num throughput global de cerca de 8%.

## 100 Reads
No teste de carga `100 Reads`, começámos por criar um Activity Provider com apenas uma oferta. Um bank e um cliente, cliente este com 100 contas e cada conta com um depósito. Criámos ainda um hotel com 100 quartos, e 100 brokers com uma atividade cada um.

De forma a exercitar o teste de carga sobre os diversos módulos, simulamos que existem 500 "utilizadores" a consultar a base de dados em simultâneo em 4 diferentes iterações.

## 30 Writes

Neste teste de carga optámos por utilizar os mesmos dados que no teste `100 Writes` para poder ser feita uma
comparação dos resultados nas mesmas condições(também realizado na mesma máquina). Acrescentou-se a leitura de carros, de taxpayers e item types fazendo com que o teste tenha 3 writes e 9 reads, ou seja, uma proporção de  23%/77% em vez dos originais 30%/70%.

De forma a exercitar o teste de carga sobre os diversos módulos, simulou-se que existem 100 "utilizadores" a consultar a base de dados em simultâneo em 4 iterações.

Verificou-se, tal como no 100 Writes, que todos os process adventures falhados ocorrem no início do teste, que se pode explicar pelo grande número de acessos concorrentes (100 utilizadores diferentes).

Ao longo do teste ocorre uma diminuição gradual dos erros ocorridos(tanto em writes como em reads) que acompanha uma diminuição progressiva dos utilizadores activos no sistema, pois é menos provável que vários utilizadores alterarem a mesma informação. Estes aspectos traduziram-se numa taxa de erro total de 3.145% que em comparação com o teste `100 Writes` é uma percentagem consideravelmente menor. Esta percentagem é explicada pela menor quantidade de escritas que este teste possui (aproximadamente menos 70% de escritas).

Observou-se um desvio padrão na execução dos process adventures(escritas) de 18377.73 milisegundos, onde um process adventures pode se executar no pior caso em 105632 milisegundos ou no melhor caso em 19 milisegundos. O throughput de escrita foi de 7.55 pedidos/segundo.

Quanto às leituras(reads), a leitura de taxpayers foi a que teve, no pior caso, um maior tempo de execução, 19828 ms, enquanto que todas as outras obtiveram valores abaixo de 3000ms. No melhor caso, a leitura mais demorada continua a ser a leitura de taxPayers com 100ms. O maior desvio padrão observado foi também para a leitura de tax payers, 4301 ms. A média do throughput das leituras foi de aproximadamente 2.6 pedidos/seg.

O throughput global(escritas + leituras) foi de cerca de 6.7 pedidos/seg.
