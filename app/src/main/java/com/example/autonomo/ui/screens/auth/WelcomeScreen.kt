package com.example.autonomo.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.autonomo.R
import com.example.autonomo.ui.components.PrimaryButton
import com.example.autonomo.ui.components.SecondaryButton
import com.example.autonomo.ui.theme.*

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Primary900, Primary700, Primary500)
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
        ) {
            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "ServiConnect",
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text      = "ServiConnect",
                style     = MaterialTheme.typography.displayMedium,
                color     = White,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(10.dp))
            Text(
                text      = "Conecta con los mejores\nproveedores de servicios\nen tu ciudad",
                style     = MaterialTheme.typography.bodyLarge,
                color     = White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )

            Spacer(Modifier.weight(1.5f))

            Card(
                shape  = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(28.dp)
                ) {
                    Text(
                        text  = "¿Listo para empezar?",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Neutral900
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text      = "Accede a tu cuenta o crea una nueva",
                        style     = MaterialTheme.typography.bodyMedium,
                        color     = Neutral50, // Ajustado para visibilidad según tu cambio previo en icon.xml
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    PrimaryButton(
                        text     = "Iniciar Sesión",
                        onClick  = onLoginClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(12.dp))
                    SecondaryButton(
                        text     = "Crear una cuenta",
                        onClick  = onRegisterClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text  = "Al continuar aceptas nuestros Términos de Servicio y Política de Privacidad",
                        style = MaterialTheme.typography.labelSmall,
                        color = Neutral400,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun WelcomeScreenPreview() {
    ServiConnectTheme {
        WelcomeScreen(onLoginClick = {}, onRegisterClick = {})
    }
}
