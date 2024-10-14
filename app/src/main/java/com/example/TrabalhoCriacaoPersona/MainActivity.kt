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

class MainActivity : ComponentActivity() {
    private lateinit var personagemService: PersonagemService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializando o serviço e o DAO
        val db = AppDatabase.getDatabase(applicationContext)
        val personagemDao = db.personagemDao()
        personagemService = PersonagemService(DistribuicaoManualStrategy(), personagemDao)

        // ID do personagem que queremos carregar
        val personagemId = 1L

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
                MainScreen(navController = navController, personagem, personagemService)
            }
            composable("second_screen") {
                SecondScreen(navController = navController, personagem, personagemService)
            }
            composable("character_sheet_screen") {
                CharacterSheetScreen(navController = navController, personagem)
            }
        }
    }

    @Composable
    fun MainScreen(
        navController: NavHostController,
        personagem: Personagem,
        personagemService: PersonagemService
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
        personagem: Personagem,
        personagemService: PersonagemService
    ) {
        var forca by remember { mutableStateOf(personagem.forca) }
        var destreza by remember { mutableStateOf(personagem.destreza) }
        var constituicao by remember { mutableStateOf(personagem.constituicao) }
        var inteligencia by remember { mutableStateOf(personagem.inteligencia) }
        var sabedoria by remember { mutableStateOf(personagem.sabedoria) }
        var carisma by remember { mutableStateOf(personagem.carisma) }

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


            AtributoAdjustableField("Força", forca, { forca = it }, personagem.modificadorForca())
            AtributoAdjustableField("Destreza", destreza, { destreza = it }, personagem.modificadorDestreza())
            AtributoAdjustableField("Constituição", constituicao, { constituicao = it }, personagem.modificadorConstituicao())
            AtributoAdjustableField("Inteligência", inteligencia, { inteligencia = it }, personagem.modificadorInteligencia())
            AtributoAdjustableField("Sabedoria", sabedoria, { sabedoria = it }, personagem.modificadorSabedoria())
            AtributoAdjustableField("Carisma", carisma, { carisma = it }, personagem.modificadorCarisma())

            Button(
                onClick = {
                    personagem.forca = forca
                    personagem.destreza = destreza
                    personagem.constituicao = constituicao
                    personagem.inteligencia = inteligencia
                    personagem.sabedoria = sabedoria
                    personagem.carisma = carisma

                    if (pontosRestantes >= 0) {
                        personagem.aplicarBonusRacial()
                        navController.navigate("character_sheet_screen")
                    }
                },
                enabled = pontosRestantes >= 0
            ) {
                Text("Confirmar Distribuição")
            }
        }
    }

    @Composable
    fun CharacterSheetScreen(
        navController: NavHostController,
        personagem: Personagem
    ) {
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

            Button(onClick = { navController.navigate("main_screen") }) {
                Text("Voltar")
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

    fun adjustedValue(baseValue: Int, modificador: Int): String {
        return (baseValue + modificador).toString()
    }
}