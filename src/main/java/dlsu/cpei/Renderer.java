/*
* This class deals with rendering a given node into a String
* That is formatted as an HTML document.
*
* According to Commonmark's Github page, https://github.com/atlassian/commonmark-java,
* A String can be converted into a node. This node is parsed by the
* already present parse method(). However, to use this class,
* the node must be passed into the renderDocument() method itself.
* */

package dlsu.cpei;

import org.commonmark.Extension;
import org.commonmark.ext.front.matter.YamlFrontMatterExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.List;

public class Renderer {
    private Parser parser;
    private HtmlRenderer renderer;
    private List<Extension> extensions;
    public Renderer(){
        extensions = Arrays.asList(StrikethroughExtension.create(),
                TablesExtension.create(),
                YamlFrontMatterExtension.create(),
                ImageAttributesExtension.create());
        this.parser = Parser.builder()
                .extensions(extensions)
                .build();
        this.renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }
    public Node convertStringToNode(String string){
        return parser.parse(string);

    }
    public String renderDocument(Node document){
        return renderer.render(document);
    }

}
