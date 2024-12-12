
# Pexels Image App

## Descrição

O **Pexels Image App** é um aplicativo Android que permite aos usuários buscar imagens da API pública do Pexels. O app utiliza o Retrofit para realizar requisições HTTP e exibir as imagens retornadas pela API em um RecyclerView. Além disso, o aplicativo oferece funcionalidades de visualização de imagens, salvar as imagens no armazenamento local, e realizar testes de carga na API pública utilizando JMeter.

## Funcionalidades

- **Buscar imagens**: O usuário pode digitar uma palavra-chave para buscar imagens na API do Pexels.
- **Exibir resultados**: As imagens retornadas pela API são exibidas em um RecyclerView.
- **Salvar imagens localmente**: O aplicativo permite salvar imagens localmente no dispositivo.
- **Testes de carga**: Utilização de JMeter para avaliar a capacidade de resposta da API pública.
- **Monitoramento de performance**: Monitoramento automatizado da API, com alertas de falhas ou degradação do desempenho.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem principal do aplicativo.
- **Retrofit**: Biblioteca para realizar requisições HTTP.
- **Room Database**: Armazenamento local para salvar as imagens.
- **MPAndroidChart**: Biblioteca para exibir gráficos de resultados de monitoramento.
- **RecyclerView**: Exibição de imagens em uma lista.
- **JMeter**: Ferramenta para realizar testes de carga na API.

## Pré-Requisitos

Antes de começar, certifique-se de ter o Android Studio instalado em sua máquina. Além disso, você precisará de uma chave de API do Pexels para realizar as requisições.

## Como Rodar o Projeto

### 1. Clonar o repositório

Clone o repositório para a sua máquina:

```bash
git clone https://github.com/seu-usuario/pexels-image-app.git
```

### 2. Configurar a chave da API do Pexels

Crie uma conta em [Pexels](https://www.pexels.com/api/) e obtenha sua chave de API. Depois, adicione sua chave de API no arquivo `RetrofitClient.kt`.

```kotlin
// RetrofitClient.kt
val pexelsApi = Retrofit.Builder()
    .baseUrl("https://api.pexels.com/v1/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()
    .create(PexelsApi::class.java)

pexelsApi.searchImages("query", "YOUR_API_KEY_HERE")
```

### 3. Sincronizar dependências

Abra o Android Studio e sincronize o projeto com as dependências. Você pode fazer isso clicando em **File > Sync Project with Gradle Files**.

### 4. Rodar o aplicativo

Conecte um dispositivo Android ou inicie um emulador, e execute o aplicativo.

### 5. Teste de Carga com JMeter

Você pode configurar e rodar um teste de carga na API do Pexels com o JMeter. O teste pode ser configurado para verificar a capacidade da API em lidar com requisições simultâneas. Um exemplo de configuração está disponível no repositório (ou você pode criar o seu próprio script no JMeter).

### 6. Monitoramento e Gráficos

O app gera gráficos de desempenho usando a biblioteca **MPAndroidChart**. Os dados do monitoramento são coletados e exibidos em tempo real na interface do aplicativo.

## Estrutura do Projeto

Aqui está a estrutura principal do seu projeto:

```
PexelsImageApp/
│
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/example/pexelsimageapp/
│   │   │   │   │   ├── MainActivity.kt         # Atividade principal do app
│   │   │   │   │   ├── RetrofitClient.kt       # Cliente Retrofit para API Pexels
│   │   │   │   │   ├── PexelsApiService.kt     # Serviço para requisições da API
│   │   │   │   │   ├── PexelsResponse.kt       # Classe para a resposta da API
│   │   │   │   │   ├── ImagesAdapter.kt        # Adapter para RecyclerView
│   │   │   │   │   ├── ImageDatabase.kt        # Banco de dados para salvar imagens localmente
│   │   │   │   │   └── ImageEntity.kt          # Entidade do banco de dados para imagem
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_main.xml       # Layout principal
│   │   │   │   └── values/
│   │   │   │       ├── strings.xml             # Arquivo de strings
├── gradle/
└── build.gradle                             # Dependências e configuração do Gradle
```

## Testes

Os testes podem ser realizados em duas frentes:

1. **Testes Unitários**: Com o auxílio de bibliotecas como JUnit, para testar as funcionalidades do Retrofit e do banco de dados.
2. **Testes de Carga**: Usando o JMeter para verificar o desempenho da API em situações de alta carga.

### Exemplo de Teste Unitário

Aqui está um exemplo de como configurar um teste simples para verificar a resposta da API:

```kotlin
@Test
fun testImageSearch() {
    val mockApiResponse = MockResponse()
        .setResponseCode(200)
        .setBody("{ 'photos': [] }")

    val mockWebServer = MockWebServer()
    mockWebServer.enqueue(mockApiResponse)
    mockWebServer.start()

    val retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pexelsApi = retrofit.create(PexelsApi::class.java)
    val response = pexelsApi.searchImages("nature").execute()

    assertTrue(response.isSuccessful)
    assertEquals(response.body()?.photos?.size, 0)

    mockWebServer.shutdown()
}
```

## Contribuindo

Se você deseja contribuir para este projeto, fique à vontade para enviar um **pull request**. Se tiver sugestões ou melhorias, crie um **issue**.

---

Esse **README** cobre a descrição do projeto, como rodar o projeto localmente, estrutura do código, e informações sobre os testes. Ele pode ser expandido conforme você adiciona mais funcionalidades ao seu aplicativo.
