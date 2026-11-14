/*
 * LIGH7NING photographic storm connection hero.
 * Project-derived code remains under GPL-3.0; see repository LICENSE.
 */
package com.aras.client.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

private val Gold = Color(0xFFE8B94F)
private val Ink = Color(0xFF050912)
private val Muted = Color(0xFF9AAAC0)

/**
 * Photographic hero: use a real storm photograph rather than procedurally drawn
 * cloud/rain/lightning doodles. The image is decorative and never participates
 * in VPN service startup or connection state.
 */
@Composable
internal fun LightningStormBanner(
    isRunning: Boolean,
    isConnecting: Boolean,
    serverName: String,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val status = when {
        isConnecting -> "ESTABLISHING SECURE TUNNEL"
        isRunning -> "CONNECTION PROTECTED"
        else -> "YOUR PRIVATE CONNECTION"
    }
    Box(
        modifier = modifier.fillMaxWidth()
            .height(374.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(Ink)
            .border(1.dp, Color(0x334F83B8), RoundedCornerShape(30.dp))
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1605727216801-e27ce1d0cc28?auto=format&fit=crop&w=1200&q=88",
            contentDescription = "Photographic night storm clouds and lightning",
            modifier = Modifier.matchParentSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Box(
            Modifier.matchParentSize().background(
                Brush.verticalGradient(
                    0f to Color(0xB8050B17),
                    0.38f to Color(0x6B071326),
                    0.72f to Color(0xA8050B17),
                    1f to Color(0xF2050912)
                )
            )
        )
        Column(
            Modifier.fillMaxWidth().align(Alignment.TopCenter)
                .padding(horizontal = 22.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                "LIGH7NING",
                color = Gold,
                fontSize = 19.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp
            )
            Text(
                status,
                color = if (isRunning) Color(0xFF9CE7C1) else Color(0xFFCFD9E8),
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )
        }
        Column(
            Modifier.align(Alignment.Center).padding(top = 34.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.size(158.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(Color(0xFF29384B), Color(0xF209111D), Color(0xFF03070E))
                        )
                    )
                    .border(2.dp, Gold.copy(alpha = if (isRunning) 1f else 0.72f), CircleShape)
                    .clickable(onClick = onToggle),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    Modifier.size(138.dp).border(
                        1.dp, Color(0x66E8B94F), CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "7",
                        color = Gold,
                        fontSize = 82.sp,
                        fontWeight = FontWeight.Black,
                        lineHeight = 86.sp
                    )
                }
            }
            Spacer(Modifier.height(18.dp))
            Text(
                when {
                    isConnecting -> "CONNECTING"
                    isRunning -> "CONNECTED"
                    else -> "TAP TO CONNECT"
                },
                color = if (isRunning) Color(0xFF9CE7C1) else Color.White,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp,
                fontSize = 12.sp
            )
            if (serverName.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(
                    serverName,
                    color = Color.White,
                    fontSize = 13.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Row(
            Modifier.align(Alignment.BottomCenter).fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("SECURE • PRIVATE • ENCRYPTED", color = Color(0xFFD5DFEB), fontSize = 9.sp, letterSpacing = 1.1.sp)
                Text("LIGH7NING NETWORK", color = Gold, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.5.sp)
            }
            Box(
                Modifier.clip(RoundedCornerShape(50)).background(Color(0xAA081321))
                    .border(1.dp, Color(0x557CA9D7), RoundedCornerShape(50))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(if (isRunning) "PROTECTED" else "READY", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            }
        }
    }
}
