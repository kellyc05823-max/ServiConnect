package com.example.autonomo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autonomo.data.OrderStatus
import com.example.autonomo.ui.theme.*

@Composable
fun AvatarInitials(
    initials: String,
    size: Int = 40,
    backgroundColor: Color = Primary600,
    textColor: Color = White,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(backgroundColor)
    ) {
        Text(
            text  = initials.take(2).uppercase(),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize   = (size * 0.35).sp,
                fontWeight = FontWeight.Bold,
                color      = textColor
            )
        )
    }
}

@Composable
fun StatusBadge(status: OrderStatus, modifier: Modifier = Modifier) {
    val (bg, fg) = when (status) {
        OrderStatus.NEW         -> Info100      to Info500
        OrderStatus.PENDING     -> Warning100   to Warning500
        OrderStatus.IN_PROGRESS -> Primary100   to Primary600
        OrderStatus.IN_REVIEW   -> Accent100    to Accent500
        OrderStatus.COMPLETED   -> Success100   to Success500
        OrderStatus.REJECTED    -> Error100     to Error500
    }
    Surface(
        color  = bg,
        shape  = RoundedCornerShape(20.dp),
        modifier = modifier
    ) {
        Text(
            text     = status.label,
            color    = fg,
            style    = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value               = value,
            onValueChange       = onValueChange,
            label               = { Text(label, style = MaterialTheme.typography.bodyMedium) },
            placeholder         = placeholder?.let { { Text(it, style = MaterialTheme.typography.bodyMedium, color = Neutral400) } },
            leadingIcon         = leadingIcon?.let { { Icon(it, contentDescription = null, modifier = Modifier.size(20.dp)) } },
            trailingIcon        = trailingContent,
            singleLine          = singleLine,
            minLines            = minLines,
            keyboardOptions     = keyboardOptions,
            visualTransformation = visualTransformation,
            isError             = isError,
            shape               = RoundedCornerShape(14.dp),
            colors              = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = Primary600,
                focusedLabelColor    = Primary600,
                focusedLeadingIconColor  = Primary600,
                unfocusedBorderColor = Neutral300,
                cursorColor          = Primary600,
                errorBorderColor     = Error500
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (isError && errorMessage.isNotEmpty()) {
            Text(
                text  = errorMessage,
                color = Error500,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Button(
        onClick  = onClick,
        enabled  = enabled,
        shape    = RoundedCornerShape(14.dp),
        colors   = ButtonDefaults.buttonColors(containerColor = Primary600),
        modifier = modifier.height(52.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
            }
            Text(text, style = MaterialTheme.typography.labelLarge)
            if (trailingContent != null) {
                Spacer(Modifier.width(8.dp))
                trailingContent()
            }
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null
) {
    OutlinedButton(
        onClick  = onClick,
        shape    = RoundedCornerShape(14.dp),
        border   = androidx.compose.foundation.BorderStroke(1.5.dp, Primary600),
        colors   = ButtonDefaults.outlinedButtonColors(contentColor = Primary600),
        modifier = modifier.height(52.dp)
    ) {
        if (leadingIcon != null) {
            Icon(leadingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun SectionHeader(
    title: String,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment    = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(title, style = MaterialTheme.typography.titleLarge)
        if (actionLabel != null && onAction != null) {
            TextButton(onClick = onAction, contentPadding = PaddingValues(0.dp)) {
                Text(
                    text  = actionLabel,
                    style = MaterialTheme.typography.labelMedium,
                    color = Primary600
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiConnectTopBar(
    title: String,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(title, style = MaterialTheme.typography.headlineSmall)
        },
        navigationIcon = {
            if (onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = "Atrás", modifier = Modifier.size(20.dp))
                }
            }
        },
        actions = actions,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor    = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun ServiceImagePlaceholder(
    color: Long,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(color), Color(color).copy(alpha = 0.7f))
                )
            ),
        content = content
    )
}

@Composable
fun EmptyStateView(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(32.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint    = Neutral300,
            modifier = Modifier.size(72.dp)
        )
        Spacer(Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, color = Neutral500, textAlign = TextAlign.Center)
        Spacer(Modifier.height(6.dp))
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = Neutral400, textAlign = TextAlign.Center)
    }
}

@Composable
fun ChatListItem(
    contactName: String,
    contactAvatar: String,
    serviceRelated: String,
    orderStatus: OrderStatus,
    lastMessage: String,
    lastMessageTime: String,
    unreadCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        AvatarInitials(initials = contactAvatar, size = 48)
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(contactName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = Neutral300)
                Text(lastMessageTime, style = MaterialTheme.typography.labelSmall, color = Neutral400)
            }
            Spacer(Modifier.height(2.dp))
            Text(serviceRelated, style = MaterialTheme.typography.labelSmall, color = Primary600)
            Spacer(Modifier.height(2.dp))
            Row(
                verticalAlignment  = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text      = lastMessage,
                    style     = MaterialTheme.typography.bodySmall,
                    color     = Neutral500,
                    maxLines  = 1,
                    overflow  = TextOverflow.Ellipsis,
                    modifier  = Modifier.weight(1f)
                )
                if (unreadCount > 0) {
                    Spacer(Modifier.width(8.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Primary600)
                    ) {
                        Text(
                            text  = unreadCount.toString(),
                            style = MaterialTheme.typography.labelSmall,
                            color = White
                        )
                    }
                }
            }
        }
    }
    HorizontalDivider(color = Neutral100, thickness = 1.dp, modifier = Modifier.padding(start = 82.dp))
}

@Composable
fun StepIndicator(
    currentStep: Int,
    totalSteps: Int,
    stepLabels: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        stepLabels.forEachIndexed { index, label ->
            val step = index + 1
            val isActive   = step == currentStep
            val isComplete = step < currentStep

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isComplete -> Primary600
                                isActive   -> Primary600
                                else       -> Neutral200
                            }
                        )
                ) {
                    if (isComplete) {
                        Icon(Icons.Rounded.Check, contentDescription = null, tint = White, modifier = Modifier.size(16.dp))
                    } else {
                        Text(
                            text  = step.toString(),
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isActive) White else Neutral400
                        )
                    }
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    text  = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isActive || isComplete) Primary600 else Neutral400
                )
            }

            if (index < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                        .padding(horizontal = 4.dp)
                        .background(if (isComplete) Primary600 else Neutral200)
                )
            }
        }
    }
}

@Composable
fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = modifier.fillMaxWidth()
    ) {
        Icon(icon, contentDescription = null, tint = Primary600, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(10.dp))
        Column {
            Text(label, style = MaterialTheme.typography.labelSmall, color = Neutral500)
            Text(value, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun GradientHeader(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Primary800, Primary600)
                )
            ),
        content = content
    )
}
