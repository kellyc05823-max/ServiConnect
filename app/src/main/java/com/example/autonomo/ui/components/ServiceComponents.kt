package com.example.autonomo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.autonomo.data.ServiceItem
import com.example.autonomo.ui.theme.*

@Composable
fun ServiceCard(
    service: ServiceItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .width(200.dp)
            .clickable(onClick = onClick)
    ) {
        ServiceImagePlaceholder(
            color = service.imageColor,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Surface(
                    color = Primary600.copy(alpha = 0.85f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text  = service.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text     = service.name,
                style    = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text     = service.description,
                style    = MaterialTheme.typography.bodySmall,
                color    = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AvatarInitials(initials = service.providerAvatar, size = 20, backgroundColor = Primary300)
                    Spacer(Modifier.width(5.dp))
                    Text(
                        text     = service.providerName.split(" ").first(),
                        style    = MaterialTheme.typography.labelSmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Rounded.Star, contentDescription = null, tint = Warning500, modifier = Modifier.size(13.dp))
                    Spacer(Modifier.width(2.dp))
                    Text(
                        text  = service.rating.toString(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun ServiceRowItem(
    service: ServiceItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        ServiceImagePlaceholder(
            color = service.imageColor,
            modifier = Modifier
                .size(72.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(service.name, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(2.dp))
            Text(service.category, style = MaterialTheme.typography.labelSmall, color = Primary600)
            Spacer(Modifier.height(2.dp))
            Text(
                text     = service.description,
                style    = MaterialTheme.typography.bodySmall,
                color    = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                AvatarInitials(initials = service.providerAvatar, size = 16, backgroundColor = Primary300)
                Spacer(Modifier.width(4.dp))
                Text(service.providerName, style = MaterialTheme.typography.labelSmall, color = Neutral500)
                Spacer(Modifier.width(10.dp))
                Icon(Icons.Rounded.Star, contentDescription = null, tint = Warning500, modifier = Modifier.size(12.dp))
                Spacer(Modifier.width(2.dp))
                Text(service.rating.toString(), style = MaterialTheme.typography.labelSmall, color = Neutral500)
            }
        }
        Text(service.price, style = MaterialTheme.typography.labelMedium, color = Primary600)
    }
}

@Composable
fun OrderCard(
    serviceName: String,
    providerName: String,
    status: com.example.autonomo.data.OrderStatus,
    date: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier  = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(serviceName, style = MaterialTheme.typography.titleSmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(3.dp))
                Text(providerName, style = MaterialTheme.typography.bodySmall, color = Neutral500)
                Spacer(Modifier.height(8.dp))
                StatusBadge(status)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(date, style = MaterialTheme.typography.labelSmall, color = Neutral400)
            }
        }
    }
}
