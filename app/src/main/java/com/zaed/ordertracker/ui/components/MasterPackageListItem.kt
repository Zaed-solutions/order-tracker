package com.zaed.ordertracker.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.WebStories
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.WebStories
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.ui.theme.ProjectTemplateTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MasterPackageList(
    modifier: Modifier = Modifier,
    masterPackages: List<MasterPackage>,
    masterPackageGroup: List<MpGroup>,
    onEditMasterPackage: (MasterPackage) -> Unit = {},
    onDeleteMasterPackage: (MasterPackage) -> Unit = {},
    onEditMasterPackageGroup: (MpGroup) -> Unit = {},
    onDeleteMasterPackageGroup: (MpGroup) -> Unit = {}
) {
    LazyColumn {
        stickyHeader {
            MasterPackageListHeader()
            HorizontalDivider()
        }
        items(masterPackageGroup) { group ->
            MasterPackageGroupListItem(
                masterPackageGroup = group,
                onEditMasterPackageGroup = onEditMasterPackageGroup,
                onDeleteMasterPackageGroup = onDeleteMasterPackageGroup
            )
            HorizontalDivider(Modifier.padding(top = 8.dp))
        }
        items(masterPackages) { mp ->
            MasterPackageListItem(
                masterPackage = mp,
                onEditMasterPackage = onEditMasterPackage,
                onDeleteMasterPackage = onDeleteMasterPackage
            )
            HorizontalDivider(Modifier.padding(top = 8.dp))
        }
    }


}

@Composable
fun MasterPackageListHeader(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Status icon column
            Spacer(Modifier.weight(1f))

            // Name column
            Text(
                text = "MP",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // KG column
            Text(
                text = "KG",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // PCS column
            Text(
                text = "PCS",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // MP KG column
            Text(
                text = "MP KG",
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Type column
            Text(
                text = "T/B",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            // Status column (for PND)
            Text(
                text = "Status",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
            // More Option
            Text(
                text = "More",
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

        }
    }
}

@Composable
fun MasterPackageListItem(
    modifier: Modifier = Modifier,
    masterPackage: MasterPackage,
    onEditMasterPackage: (MasterPackage) -> Unit = {},
    onDeleteMasterPackage: (MasterPackage) -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 8.dp)
    ) {
        //STATUS ICON
        Icon(
            imageVector = if (masterPackage.isExported) Icons.Filled.Circle else Icons.Outlined.Circle,
            contentDescription = "MP Status",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
        )

        //MP ICON
        Surface(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
        ) {
            Text(
                text = masterPackage.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        //KG
        Text(
            text = masterPackage.shipments.sumOf { it.weight }.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        //PCS
        Text(
            text = masterPackage.count.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        //MP KG
        Text(
            text = masterPackage.weightKg.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        //T/B
        Surface(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(8.dp),
            color = when (masterPackage.type) {
                "T" -> Color.Blue
                else -> Color.Yellow
            }
        ) {
            Text(
                text = masterPackage.type,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = when (masterPackage.type) {
                    "T" -> Color.White
                    else -> Color.Black
                }
            )
        }
        //PENDING STATUS
        if (masterPackage.isPnd)
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.Red
            ) {
                Text(
                    text = "PND",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        else
            Spacer(Modifier.weight(1f))
        MoreDropDownMenu(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            items = listOf(
                MoreDropdownItem(
                    title = stringResource(R.string.edit),
                    icon = Icons.Default.Edit,
                    onClick = { onEditMasterPackage(masterPackage) },
                    tint = MaterialTheme.colorScheme.primary,
                ),
                MoreDropdownItem(
                    title = stringResource(R.string.delete),
                    icon = Icons.Default.DeleteOutline,
                    onClick = { onDeleteMasterPackage(masterPackage) },
                    tint = MaterialTheme.colorScheme.error,
                ),
            ),
        )

    }
}

@Composable
fun MasterPackageGroupListItem(
    modifier: Modifier = Modifier,
    masterPackageGroup: MpGroup,
    onEditMasterPackageGroup: (MpGroup) -> Unit = {},
    onDeleteMasterPackageGroup: (MpGroup) -> Unit = {},
) {
    val pndCount = remember { masterPackageGroup.masterPackages.count { it.isPnd } }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 8.dp)
    ) {
        //STATUS ICON
        Icon(
            imageVector = if (masterPackageGroup.isExported) Icons.Default.WebStories else Icons.Outlined.WebStories,
            contentDescription = "MP Status",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        //MP ICON
        Surface(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color(masterPackageGroup.color),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            Text(
                text = masterPackageGroup.name,
                textAlign = TextAlign.Center,
                color = contentColorFor(Color(masterPackageGroup.color)),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        //KG
        Text(
            text = masterPackageGroup.masterPackages.sumOf { it.shipments.sumOf { it.weight } }
                .toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        //PCS
        Text(
            text = masterPackageGroup.masterPackages.sumOf { it.count }.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        //MP KG
        Text(
            text = "-",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        )
        //T/B
        Surface(
            modifier = Modifier
                .weight(1f)
                .wrapContentSize()
                .padding(horizontal = 8.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        ) {
            Text(
                text = masterPackageGroup.name + masterPackageGroup.masterPackages.size,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
        }
        //PENDING STATUS
        if (pndCount > 0)
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp),
                color = Color.Red
            ) {
                Text(
                    text = "$pndCount PND",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        else
            Spacer(Modifier.weight(1f))

        MoreDropDownMenu(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            items = listOf(
                MoreDropdownItem(
                    title = stringResource(R.string.edit),
                    icon = Icons.Default.Edit,
                    onClick = { onEditMasterPackageGroup(masterPackageGroup) },
                    tint = MaterialTheme.colorScheme.primary,
                ),
                MoreDropdownItem(
                    title = stringResource(R.string.delete),
                    icon = Icons.Default.DeleteOutline,
                    onClick = { onDeleteMasterPackageGroup(masterPackageGroup) },
                    tint = MaterialTheme.colorScheme.error,
                ),
            ),
        )

    }
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun MpItemPreview() {
    ProjectTemplateTheme {
        MasterPackageList(
            masterPackages = listOf(
                MasterPackage(
                    id = "1",
                    name = "Group1",
                    count = 10,
                    type = "T",
                    isPnd = false,
                    weightKg = 50.5
                ),
                MasterPackage(
                    id = "2",
                    name = "Group2",
                    count = 20,
                    type = "T",
                    isPnd = true,
                    weightKg = 60.5
                )
            ),
            masterPackageGroup = listOf(
                MpGroup(
                    id = "1",
                    isExported = true,
                    name = "Group1",
                    color = 0xFF2196F3.toInt(),
                    masterPackages = listOf(
                        MasterPackage(
                            id = "1",
                            name = "MP1",
                            count = 10,
                            type = "T",
                            isPnd = false,
                            isExported = true
                        ),
                        MasterPackage(
                            id = "2",
                            name = "MP2",
                            count = 20,
                            type = "T",
                            isPnd = true
                        )
                    ),
                ),


                )
        )

    }

}