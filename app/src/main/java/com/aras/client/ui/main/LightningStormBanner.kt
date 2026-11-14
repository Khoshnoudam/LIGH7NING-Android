/*
 * LIGH7NING branded connection hero.
 * Derived from ArasClient; this modification is distributed under GPL-3.0.
 */
package com.aras.client.ui.main

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val StormNavy = Color(0xFF07142B)
private val StormBlue = Color(0xFF174477)
private val StormIce = Color(0xFF9CCBFA)
private val StormGold = Color(0xFFFFC95B)

@Composable
internal fun LightningStormBanner(
    isRunning: Boolean,
    isConnecting: Boolean,
    serverName: String,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "LIGH7NING storm")
    val drift by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(18000, easing = LinearEasing)),
        label = "cloud drift"
    )
    val glow by transition.animateFloat(
        initialValue = 0.52f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2400), RepeatMode.Reverse),
        label = "storm glow"
    )
    val flash by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(5600, easing = LinearEasing)),
        label = "lightning flash"
    )
    val statusColor = when {
        isRunning -> Color(0xFF62E7A7)
        isConnecting -> StormIce
        else -> Color(0xFFB6C1D2)
    }

    Column(
        modifier = modifier.fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF142D50), Color(0xFF0A1930), Color(0xFF050D1D))
                ),
                RoundedCornerShape(28.dp)
            )
            .border(1.dp, Color(0x445A9DE8), RoundedCornerShape(28.dp))
            .padding(horizontal = 18.dp, vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(
            text = "LIGH7NING",
            color = StormGold,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 4.sp,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "SECURE CONNECTION",
            color = Color(0xFF8CA9CA),
            fontSize = 9.sp,
            letterSpacing = 2.4.sp
        )
        Box(
            Modifier.fillMaxWidth().height(166.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(Modifier.size(194.dp)) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val radius = size.minDimension * 0.29f
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(
                            StormBlue.copy(alpha = 0.42f * glow),
                            StormBlue.copy(alpha = 0.08f),
                            Color.Transparent
                        ),
                        center,
                        radius * 2.35f
                    ),
                    center = center,
                    radius = radius * 2.35f
                )
                for (i in 0..19) {
                    val direction = if (i % 2 == 0) 1.0 else -1.0
                    val angle = (i * 2.0 * PI / 20.0 + drift * 0.8 * direction).toFloat()
                    val distance = radius * (0.72f + (i % 4) * 0.11f)
                    val start = Offset(
                        center.x + distance * cos(angle),
                        center.y + distance * sin(angle)
                    )
                    val end = Offset(
                        center.x + distance * 1.52f * cos(angle + 0.48f),
                        center.y + distance * 1.52f * sin(angle + 0.48f)
                    )
                    val mist = Path().apply {
                        moveTo(start.x, start.y)
                        quadraticBezierTo(
                            center.x + distance * 1.8f * cos(angle + 0.18f),
                            center.y + distance * 1.8f * sin(angle + 0.18f),
                            end.x, end.y
                        )
                    }
                    drawPath(
                        mist,
                        StormIce.copy(alpha = (0.08f + (i % 3) * 0.025f) * glow),
                        style = Stroke(width = (8 + i % 3 * 3).dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                for (i in 0..31) {
                    val angle = (i * 2.0 * PI / 32.0 + drift * 0.52).toFloat()
                    val distance = radius * (1.18f + (i % 4) * 0.13f)
                    val pulse = (0.38 + 0.28 * (1.0 + sin(drift * 2.0 * PI + i))).toFloat()
                    drawCircle(
                        StormGold.copy(alpha = pulse * if (isRunning) 1f else 0.65f),
                        radius = (if (i % 7 == 0) 1.8f else 1.0f).dp.toPx(),
                        center = Offset(
                            center.x + distance * cos(angle),
                            center.y + distance * sin(angle)
                        )
                    )
                }
                drawCircle(
                    Color(0xFF5CA8F2).copy(alpha = 0.24f * glow),
                    radius * 1.12f,
                    center,
                    style = Stroke(width = 1.1.dp.toPx())
                )
                val bolt = Path().apply {
                    moveTo(center.x + radius * 0.48f, center.y - radius * 1.12f)
                    lineTo(center.x + radius * 0.04f, center.y - radius * 0.20f)
                    lineTo(center.x + radius * 0.42f, center.y - radius * 0.17f)
                    lineTo(center.x - radius * 0.26f, center.y + radius * 1.12f)
                    lineTo(center.x - radius * 0.02f, center.y + radius * 0.22f)
                    lineTo(center.x - radius * 0.37f, center.y + radius * 0.18f)
                    close()
                }
                val brightFlash = flash in 0.78f..0.84f || flash in 0.92f..0.95f
                drawPath(
                    bolt,
                    StormGold.copy(alpha = if (brightFlash) 0.98f else 0.72f),
                    style = Stroke(width = 2.4.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            Box(
                modifier = Modifier.size(70.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(Color(0xFF244D78), Color(0xFF09182E))
                        )
                    )
                    .border(1.5.dp, StormGold.copy(alpha = 0.9f), CircleShape)
                    .clickable(onClick = onToggle),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "⚡",
                    fontSize = 31.sp,
                    color = StormGold
                )
            }
        }
        Text(
            text = when {
                isConnecting -> "CONNECTING"
                isRunning -> "PROTECTED"
                else -> "READY TO CONNECT"
            },
            color = statusColor,
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.3.sp,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = serverName.ifBlank { "Choose a server below" },
            color = Color(0xFFB9CBE0),
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1
        )
        Text(
            text = if (isRunning || isConnecting) "TAP THE CORE TO DISCONNECT" else "TAP THE CORE TO CONNECT",
            color = Color(0xFF7894B5),
            fontSize = 8.sp,
            letterSpacing = 1.4.sp
        )
    }
}
