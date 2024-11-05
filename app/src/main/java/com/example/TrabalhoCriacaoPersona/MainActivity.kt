package com.example.trabalhocriacaopersona

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dnd.model.Personagem
import com.dnd.service.PersonagemService
import com.dnd.strategy.DistribuicaoManualStrategy
import com.dnd.model.AppDatabase
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import com.example.trabalhocriacaopersona.utils.RacaUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.floor
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.dnd.model.PersonagemEntity
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
    private lateinit var personagemService: PersonagemService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializando o serviço e o DAO
        val db = AppDatabase.getDatabase(applicationContext)
        val personagemDao = db.personagemDao()
        personagemService = PersonagemService(DistribuicaoManualStrategy(), personagemDao)

        // ID do personagem que queremos carregar
        val personagemId = 0L

        // Carregar o personagem de forma assíncrona
        lifecycleScope.launch {
            val personagem = withContext(Dispatchers.IO) {
                personagemService.carregarPersonagem(personagemId) ?: Personagem()
            }

            // Setar a UI e passar o personagem carregado
            setContent {
                MaterialTheme {
                    val navController = rememberNavController()
                    SetupNavGraph(navController, personagem, personagemService)
                }
            }
        }
    }

    @Composable
    fun SetupNavGraph(
        navController: NavHostController,
        personagem: Personagem,
        personagemService: PersonagemService
    ) {
        NavHost(navController = navController, startDestination = "main_screen") {
            composable("main_screen") {
                MainScreen(navController = navController, personagemService = personagemService, personagem = personagem)
            }
            composable("second_screen") {
                SecondScreen(navController = navController, personagemService = personagemService, personagem = personagem)
            }
            composable("character_sheet_screen") {
                CharacterSheetScreen(navController = navController, personagemService = personagemService, personagem = personagem)
            }
            composable("lista_personagens_screen") {
                ListaPersonagensScreen(navController = navController, personagemService = personagemService)
            }
        }
    }


    @Composable
    fun MainScreen(
        navController: NavHostController,
        personagemService: PersonagemService,
        personagem: Personagem
    ) {
        var nome by remember { mutableStateOf(personagem.nome ?: "") }
        var racaSelecionada by remember { mutableStateOf(personagem.raca) }
        var erro by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do Personagem") },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.7f)
            )

            Text(text = "Escolha a Raça:")
            DropdownRaceSelector(
                selectedRace = racaSelecionada?.nome ?: "Selecione uma Raça"
            ) { newRaca ->
                racaSelecionada = RacaUtil.obterRacaPorNome(newRaca)
            }

            // Exibir os bônus raciais após a seleção da raça
            racaSelecionada?.let { raca ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Bônus Raciais:", style = MaterialTheme.typography.headlineSmall)
                val bonusRaciais = raca.obterBonusRacial()
                bonusRaciais.forEach { (atributo, bonus) ->
                    Text(text = "$atributo: +$bonus")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                if (nome.isNotEmpty() && racaSelecionada != null) {
                    personagem.nome = nome
                    personagem.raca = racaSelecionada
                    erro = false

                    navController.navigate("second_screen")
                } else {
                    erro = true
                }
            }) {
                Text("Confirmar")
            }

            if (erro) {
                Text(
                    text = "Por favor, insira um nome válido e selecione uma raça",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    @Composable
    fun SecondScreen(
        navController: NavHostController,
        personagemService: PersonagemService,
        personagem: Personagem? = navController.previousBackStackEntry
            ?.arguments
            ?.getParcelable<Personagem>("personagem")
    ) {
        fun calculoModificador(valor: Int):  Int = floor((valor - 10) / 2.0).toInt()

        personagem?.let {
            var forca by remember { mutableStateOf(it.forca) }
            var destreza by remember { mutableStateOf(it.destreza) }
            var constituicao by remember { mutableStateOf(it.constituicao) }
            var inteligencia by remember { mutableStateOf(it.inteligencia) }
            var sabedoria by remember { mutableStateOf(it.sabedoria) }
            var carisma by remember { mutableStateOf(it.carisma) }

            val totalPontos = 27
            val pontosUsados = personagemService.calcularCusto(
                mapOf(
                    "forca" to forca,
                    "destreza" to destreza,
                    "constituicao" to constituicao,
                    "inteligencia" to inteligencia,
                    "sabedoria" to sabedoria,
                    "carisma" to carisma
                )
            )
            val pontosRestantes = totalPontos - pontosUsados

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Text(text = "Pontos restantes: $pontosRestantes")

                AtributoAdjustableField("Força", forca, { forca = it }, calculoModificador(forca))
                AtributoAdjustableField("Destreza", destreza, { destreza = it }, calculoModificador(destreza))
                AtributoAdjustableField("Constituição", constituicao, { constituicao = it }, calculoModificador(constituicao))
                AtributoAdjustableField("Inteligência", inteligencia, { inteligencia = it }, calculoModificador(inteligencia))
                AtributoAdjustableField("Sabedoria", sabedoria, { sabedoria = it }, calculoModificador(sabedoria))
                AtributoAdjustableField("Carisma", carisma, { carisma = it }, calculoModificador(carisma))

                Button(
                    onClick = {
                        it.forca = forca
                        it.destreza = destreza
                        it.constituicao = constituicao
                        it.inteligencia = inteligencia
                        it.sabedoria = sabedoria
                        it.carisma = carisma

                        if (pontosRestantes >= 0) {
                            it.aplicarBonusRacial()

                            // Salva automaticamente o personagem após a redistribuição
                            personagemService.salvarPersonagem(it)

                            // Atualiza automaticamente o personagem após a redistribuição
                            //personagemService.atualizarPersonagem(it)

                            navController.navigate("character_sheet_screen")
                        }
                    },
                    enabled = pontosRestantes >= 0
                ) {
                    Text("Confirmar Distribuição")
                }
            }
        } ?: run {
            Text("Erro: Personagem não encontrado.")
        }
    }

    @Composable
    fun CharacterSheetScreen(
        navController: NavHostController,
        personagemService: PersonagemService,
        personagem: Personagem
    ) {
        var personagens by remember { mutableStateOf<List<PersonagemEntity>>(emptyList()) }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Ficha do Personagem", style = MaterialTheme.typography.headlineMedium)

            Text("Nome: ${personagem.nome}")
            Text("Raça: ${personagem.raca?.nome}")
            Text("Força: ${adjustedValue(personagem.forca, personagem.modificadorForca())}")
            Text("Destreza: ${adjustedValue(personagem.destreza, personagem.modificadorDestreza())}")
            Text("Constituição: ${adjustedValue(personagem.constituicao, personagem.modificadorConstituicao())}")
            Text("Inteligência: ${adjustedValue(personagem.inteligencia, personagem.modificadorInteligencia())}")
            Text("Sabedoria: ${adjustedValue(personagem.sabedoria, personagem.modificadorSabedoria())}")
            Text("Carisma: ${adjustedValue(personagem.carisma, personagem.modificadorCarisma())}")
            Text("Pontos de Vida: ${personagem.pontosDeVida}")

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                navController.navigate("lista_personagens_screen")
            }) {
                Text("Visualizar os personagens")
            }
        }
    }

    @Composable
    fun DropdownRaceSelector(
        selectedRace: String,
        onRaceSelected: (String) -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }
        val races = listOf(
            "Humano", "Elfo", "Alto Elfo", "Anão", "Halfling",
            "Anão da Montanha", "Draconato", "Meio-Orc", "Gnomo da Floresta",
            "Halfling Robusto", "Gnomo das Rochas", "Gnomo", "Tiefling",
            "Anão da Colina", "Elfo da Floresta", "Meio-Elfo", "Drow",
            "Halfling Pés Leves"
        )

        Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
            TextButton(onClick = { expanded = true }) {
                Text(text = selectedRace)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                races.forEach { race ->
                    DropdownMenuItem(
                        text = { Text(race) },
                        onClick = {
                            onRaceSelected(race)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    @Composable
    fun ListaPersonagensScreen(
        navController: NavHostController,
        personagemService: PersonagemService
    ) {
        var personagens by remember { mutableStateOf<List<PersonagemEntity>>(emptyList()) }

        LaunchedEffect(Unit) {
            personagens = withContext(Dispatchers.IO) {
                personagemService.getAllPersonagens()
            }
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text("Lista de Personagens Criados", style = MaterialTheme.typography.headlineMedium)

            if (personagens.isNotEmpty()) {
                personagens.forEach { personagemEntity ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("ID: ${personagemEntity.id} - Nome: ${personagemEntity.nome}")

                        val personagem = personagemEntity.toPersonagem()

                        Row {
                            Button(onClick = {
                                val personagem = personagemEntity.toPersonagem()
                                navController.currentBackStackEntry?.arguments?.putParcelable("personagem", personagem)
                                navController.navigate("second_screen")
                            }) {
                                Text("Atualizar")
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(onClick = {
                                // Usar CoroutineScope para operações de I/O
                                CoroutineScope(Dispatchers.IO).launch {
                                    personagemService.deletePersonagemById(personagemEntity.id)
                                    personagens = personagens.filter { it.id != personagemEntity.id }
                                }
                            }) {
                                Text("Deletar")
                            }
                        }
                    }
                }
            } else {
                Text("Nenhum personagem criado.")
            }

            Button(onClick = { navController.navigate("main_screen") }) {
                Text("Voltar")
            }
        }
    }



    @Composable
    fun AtributoAdjustableField(label: String, valor: Int, onValorChange: (Int) -> Unit, modificador: Int) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "$label: $valor (Modificador: ${if (modificador >= 0) "+$modificador" else "$modificador"})")
            Row {
                Button(onClick = { if (valor > 8) onValorChange(valor - 1) }) {
                    Icon(Icons.Filled.Remove, contentDescription = "Diminuir")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(onClick = { if (valor < 15) onValorChange(valor + 1) }) {
                    Icon(Icons.Filled.Add, contentDescription = "Aumentar")
                }
            }
        }
    }

    /*fun adjustedValue(baseValue: Int, modificador: Int): String {
        return (baseValue + modificador).toString()
    }*/

    fun adjustedValue(baseValue: Int, modificador: Int): Int {
        return baseValue + modificador
    }

}