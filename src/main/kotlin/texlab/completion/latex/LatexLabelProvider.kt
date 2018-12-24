package texlab.completion.latex

import org.eclipse.lsp4j.CompletionItem
import texlab.LatexDocument
import texlab.completion.CompletionItemFactory
import texlab.completion.CompletionRequest
import texlab.syntax.latex.LatexCommandSyntax

class LatexLabelProvider : LatexArgumentProvider() {

    override val commandNames: List<String> = listOf("\\ref", "\\autoref")

    override val argumentIndex: Int = 0

    override fun getItems(request: CompletionRequest, command: LatexCommandSyntax): List<CompletionItem> {
        return request.relatedDocuments
                .filterIsInstance<LatexDocument>()
                .flatMap { it.tree.labelDefinitions }
                .map { it.name.text }
                .distinct()
                .map { CompletionItemFactory.createLabel(it) }
    }
}