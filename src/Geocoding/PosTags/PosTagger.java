package Geocoding.PosTags;

/**
 * Created by alistair on 16/04/2017.
 */
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

    public class PosTagger  {

            public static MaxentTagger tagger = new MaxentTagger(
                    "/Users/alistair/Documents/DissResources/stanford-postagger-2016-10-31/models/english-bidirectional-distsim.tagger");

    }
