/*
 * LIGH7NING storm banner.
 * Derived from the ArasClient GPL-3.0 project; modifications are provided under GPL-3.0.
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition(label = "LIGH7NING storm")
    val drift by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(14000, easing = LinearEasing)),
        label = "cloud drift"
    )
    val glow by transition.animateFloat(
        initialValue = 0.55f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(2200), RepeatMode.Reverse),
        label = "storm glow"
    )
    val flash by transition.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(5200, easing = LinearEasing)),
        label = "lightning flash"
    )
    val connected = isRunning
    val statusColor = when {
        connected -> Color(0xFF66E6A8)
        isConnecting -> StormIce
        else -> Color(0xFFB6C1D2)
    }
    Column(
        modifier = modifier.fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF10294B), StormNavy, Color(0xFF08152A))),
                RoundedCornerShape(24.dp)
            ).padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = "LIGH7NING",
            color = StormGold,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 4.sp,
            style = MaterialTheme.typography.titleMedium
        )
        Box(Modifier.fillMaxWidth().height(116.dp), contentAlignment = Alignment.Center) {
            Canvas(Modifier.size(170.dp)) {
                val center = Offset(size.width / 2f, size.height / 2f)
                val radius = size.minDimension * 0.29f
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(StormBlue.copy(alpha = 0.34f * glow), StormBlue.copy(alpha = 0.05f), Color.Transparent),
                        center, radius * 2.2f
                    ), center = center, radius = radius * 2.2f
                )
                for (i in 0..17) {
                    val angle = (i * 2.0 * PI / 18.0 + drift * 0.7 * if (i % 2 == 0) 1 else -1).toFloat()
                    val d = radius * (0.78f + (i % 4) * 0.10f)
                    val start = Offset(center.x + d * cos(angle), center.y + d * sin(angle))
                    val end = Offset(center.x + d * 1.45f * cos(angle + 0.52f), center.y + d * 1.45f * sin(angle + 0.52f))
                    val cloud = Path().apply {
                        moveTo(start.x, start.y)
                        quadraticBezierTo(center.x + d * 1.8f * cos(angle + 0.2f), center.y + d * 1.8f * sin(angle + 0.2f), end.x, end.y)
                    }
                    drawPath(cloud, StormIce.copy(alpha = (0.07f + (i % 3) * 0.025f) * glow),
                        style = Stroke(width = (8 + i % 3 * 3).dp.toPx(), cap = StrokeCap.Round))
                }
                for (i in 0..23) {
                    val angle = (i * 2.0 * PI / 24.0 + drift * 0.5).toFloat()
                    val d = radius * (1.15f + (i % 4) * 0.12f)
                    val alpha = (0.35f + 0.3f * (1f + sin(drift * 2.0 * PI + i))).toFloat()
                    drawCircle(StormGold.copy(alpha = alpha * if (connected) 0.95f else 0.62f),
                        radius = (if (i % 6 == 0) 1.8f else 1.1f).dp.toPx(),
                        center = Offset(center.x + d * cos(angle), center.y + d * sin(angle)))
                }
                val bolt = Path().apply {
                    moveTo(center.x + radius * 0.52f, center.y - radius * 1.12f)
                    lineTo(center.x + radius * 0.05f, center.y - radius * 0.22f)
                    lineTo(center.x + radius * 0.43f, center.y - radius * 0.18f)
                    lineTo(center.x - radius * 0.25f, center.y + radius * 1.12f)
                    lineTo(center.x - radius * 0.02f, center.y + radius * 0.22f)
                    lineTo(center.x - radius * 0.36f, center.y + radius * 0.18f)
                    close()
                }
                val isFlash = flash in 0.78f..0.83f || flash in 0.91f..0.94f
                drawPath(bolt, StormGold.copy(alpha = if (isFlash && connected) 0.95f else 0.58f),
                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round))
            }
        }
        Text(
            text = when {
                isConnecting -> "CONNECTING"
                isRunning -> "PROTECTED"
                else -> "READY TO CONNECT"
            },
            color = statusColor,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 2.sp,
            style = MaterialTheme.typography.labelMedium
        )
        if (serverName.isNotBlank()) {
            Text(serverName, color = Color(0xFFB9CBE0), style = MaterialTheme.typography.bodySmall,
                maxLines = 1)
        } else {
            Text("Choose a server below", color = Color(0xFF8EA5C1), style = MaterialTheme.typography.bodySmall)
        }
    }
}
