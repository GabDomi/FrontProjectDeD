# 🎲 Criador de Personagens D&D

Este projeto é um sistema interativo para criação de personagens baseado no universo de **Dungeons & Dragons (D&D)**. Ele permite que os jogadores escolham uma raça, distribuam pontos em atributos e salvem seus personagens no banco de dados local.

O código implementa uma arquitetura baseada em **MVVM**, utilizando **Jetpack Compose** para a interface e **Room** para persistência dos dados. Além disso, o sistema de distribuição de atributos segue a regra **Point Buy (27 pontos)**.

---

## 📌 Funcionalidades
✅ **Escolha de Raça** - Diferentes raças com bônus raciais específicos.
✅ **Distribuição de Pontos** - Sistema de compra de pontos baseado nas regras de D&D.
✅ **Banco de Dados Local** - Uso de Room para persistência dos personagens criados.
✅ **Interface Moderna** - Construída com Jetpack Compose.
✅ **Gerenciamento de Personagens** - Criar, visualizar, editar e excluir personagens salvos.

---

## 🏗 Estrutura do Código

```
📦 src
 ┣ 📂 racas                  # Implementação das raças jogáveis
 ┣ 📂 model                  # Definição de classes e banco de dados
 ┣ 📂 service                # Gerenciamento de criação e edição de personagens
 ┣ 📂 strategy               # Estratégia de distribuição de pontos
 ┣ 📂 utils                  # Utilitários auxiliares
 ┣ 📜 MainActivity.kt        # Atividade principal e navegação
```

---

## ⚔️ Regras do Jogo

### 🎯 Distribuição de Pontos
- O personagem começa com **8 pontos em todos os atributos**.
- O jogador tem **27 pontos** para distribuir.
- O custo de cada aumento de atributo segue esta tabela:

| Valor | Custo |
|--------|--------|
| 8  | 0 |
| 9  | 1 |
| 10 | 2 |
| 11 | 3 |
| 12 | 4 |
| 13 | 5 |
| 14 | 7 |
| 15 | 9 |

- O **máximo permitido antes dos bônus raciais é 15**.
- Se os pontos distribuídos **excederem 27**, a distribuição não será permitida.

### 🏹 Bônus Raciais
Cada raça concede **bônus específicos** nos atributos, como definido no código:

- **Humano** → +1 em todos os atributos.
- **Meio-Orc** → +2 Força, +1 Constituição.
- **Elfo** → +2 Destreza, +1 Inteligência.
- **Tiefling** → +2 Carisma, +1 Inteligência.

### 🔢 Cálculo de Modificadores
Os modificadores dos atributos seguem a fórmula:
```
(valor - 10) / 2 (arredondado para baixo)
```
Exemplo:
- Força = 14 → Modificador **+2**
- Destreza = 10 → Modificador **0**

---

## 🛠 Tecnologias Utilizadas
- **Kotlin** 🟡
- **Jetpack Compose** 🎨 (UI declarativa)
- **Android Room** 🗄️ (banco de dados local)
- **Arquitetura MVVM** 🏗
- **Coroutines** ⚡ (operações assíncronas)

---

## 🚀 Como Executar o Projeto

1️⃣ Clone este repositório:
```bash
git clone https://github.com/seu-usuario/criador-personagens-dnd.git
cd criador-personagens-dnd
```

2️⃣ Abra no **Android Studio** e compile o projeto.

3️⃣ Execute no emulador ou em um dispositivo físico.

