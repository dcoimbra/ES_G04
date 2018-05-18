## Introdução
As soluções para os problemas de concurrência offline dividem-se em dois modelos principais: Optimistic Offline Lock e Pessimistic Offline Lock.
Na solução pessimista o lock é adquirido no início da transação de negócio antes de usar os dados. Consequentemente é apenas permitida a uma transação de cada vez ter acesso aos dados, mas com a vantagem de garantir a persistência dos dados alterados, porque se garante que não existem duas transações a trabalhar os mesmos dados ao mesmo tempo.

Na solução otimista os conflitos sao detetados imediatamente antes do commit, fazendo roll-back da transação, e garantindo que as alterações a ser persistidas por uma sessão não entram em conflito com a de outra sessão.

A Fénix Framework utiliza uma abordagem Otimista e ainda implementa outros mecanismos, nomeadamente, Coarse-Grained Lock e Implicit Lock. Estes mecanismos permitem adquirir o lock vários objetos relacionados, sem ter que os adquirir individualmente, e fatorizar a codificação dos locks de modo a que os mecanismos de controlo de concorrência sejam implícitos à framework.


## 100 writes
Para a execução do teste criamos um activity provider que vai possuir 100 actividades criadas, um bank com todos os clientes do sistema (activity provider, hotel, RentACar, 100 brokers e 1 cliente) um hotel com os vários quartos disponiveis, um RentACar com todos os cars, um broker com várias adventures constituidas pelos vários elementos criados anteriormente e de seguida colocou-se o cliente a fazer 100 process adventures concorrentes.

Como a fénix-framework segue uma politica optimista, de coarse-grained lock, ao fazer os 100 writes simultaneamente são gerados vários conflitos quando as transações tentam fazer commit para a base de dados e é verificado que mais do que um utilizadores alterou a mesma informação. Verificou-se que todos os process adventures falhados ocorrem no inicio do teste, que se pode explicar pelo grande número de acessos concorrentes ao inicio do teste, 100 utilizadores diferentes, ocorrendo uma diminuição progressiva dos erros ocorridos que acompanha uma diminuição progressiva dos utilizadores activos no sistema, pois existe menos probabilidade de vários utilizadores alterarem a mesma informação, podendo gerar um erro. Estes aspectos traduziram-se numa taxa de erro de 39,9% o que é um valor bastante elevado.

Observou-se um desvio padrão na execução dos process adventures de 10847.82 milisegundos, onde um process adventures pode se executar no pior caso em 57108 milisegundos, num sistema sobrelotado (com 100 utilizadores) ou no melhor caso em 33 milisegundos num sistema a trabalhar dentro do seu intervalo de confiança, o que se traduz num throughput global de cerca de 8%.
