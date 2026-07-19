package com.aimall.aimall.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aimall.aimall.model.Order;
import com.aimall.aimall.model.Product;
import com.aimall.aimall.repository.OrderRepository;
import com.aimall.aimall.repository.ProductRepository;

@Service
public class AimallAIService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Process user query and generate AI response
     * Supports queries about products, orders, recommendations, help, etc.
     */
    public String processQuery(String query, Long userId) {
        if (query == null || query.trim().isEmpty()) {
            return "Hey! I'm VIYA (Your Virtual AI Assistant)! How can I help you today? 😊\n\nYou can ask me about:\n✅ Product recommendations\n✅ Your orders\n✅ Best deals\n✅ Shopping help\n✅ World knowledge (history, science, tech, culture)\n✅ Delivery status";
        }

        String lowerQuery = query.toLowerCase().trim();

        // Greeting queries
        if (isGreeting(lowerQuery)) {
            return "Namaste! 🙏 I'm VIYA, your personal shopping assistant & knowledge buddy! I'm here to help you find amazing products, track your orders, answer your questions about anything in the world, and make your shopping experience awesome! What can I help you with today?";
        }

        // Help queries
        if (isHelpQuery(lowerQuery)) {
            return "I'm VIYA - Your Virtual AI Assistant! Here's what I can do:\n\n📦 **Orders & Tracking**: Ask about your orders, delivery status\n🛍️ **Shopping**: Get product recommendations, search help\n💰 **Deals**: Find best prices, discounts, trending products\n🌍 **World Knowledge**: Ask me about history, science, technology, culture, sports, geography, and more!\n❓ **General Help**: Any shopping or general questions\n\nJust ask me anything! 😊";
        }

        // General knowledge queries
        if (isGeneralKnowledgeQuery(lowerQuery)) {
            return getGeneralKnowledge(lowerQuery);
        }

        // Product search/recommendations
        if (isProductQuery(lowerQuery)) {
            return getProductRecommendations(lowerQuery);
        }

        // Order tracking
        if (isOrderQuery(lowerQuery) && userId != null) {
            return getOrderInfo(userId);
        }

        // Price/deals queries
        if (isPriceQuery(lowerQuery)) {
            return getBestDeals();
        }

        // Default helpful response
        return generateGenericResponse(lowerQuery);
    }


    private boolean isGreeting(String query) {
        return query.matches(".*(hello|hi|hey|namaste|hola|assalamualaikum|kya hal|kaise ho).*");
    }

    private boolean isGeneralKnowledgeQuery(String query) {
        return query.matches(".*(history|geography|science|technology|physics|chemistry|biology|mathematics|space|universe|planet|animal|plant|country|capital|famous|invention|discovery|culture|religion|education|sport|game|movie|music|art|literature|biography|important|question|tell|what|who|when|where|why|how|kya|kaun|kahan|kab|kyun|kaise).*");
    }

    private String getGeneralKnowledge(String query) {
        String lowerQuery = query.toLowerCase();

        // History questions
        if (lowerQuery.matches(".*(history|historical|ancient|medieval|modern|independence|partition|freedom).*")) {
            return getHistoryKnowledge(lowerQuery);
        }

        // Geography questions
        if (lowerQuery.matches(".*(geography|country|city|capital|mountain|river|ocean|continent|map|border).*")) {
            return getGeographyKnowledge(lowerQuery);
        }

        // Science & Technology
        if (lowerQuery.matches(".*(science|technology|physics|chemistry|biology|quantum|atom|space|rocket|ai|computer|internet|tech).*")) {
            return getScienceTechnologyKnowledge(lowerQuery);
        }

        // Sports & Entertainment
        if (lowerQuery.matches(".*(sport|game|cricket|football|basketball|movie|actor|music|singer|celebrity|olympics|tournament).*")) {
            return getSportsEntertainmentKnowledge(lowerQuery);
        }

        // Famous People & Biography
        if (lowerQuery.matches(".*(person|person|leader|scientist|inventor|actor|musician|artist|famous|biography|life).*")) {
            return getFamousPeopleKnowledge(lowerQuery);
        }

        // Nature & Animals
        if (lowerQuery.matches(".*(animal|nature|wildlife|bird|fish|mammal|reptile|ecosystem|environment|ecology).*")) {
            return getNatureKnowledge(lowerQuery);
        }

        // Economy & Business
        if (lowerQuery.matches(".*(economy|business|company|startup|market|stock|investment|entrepreneurship|finance).*")) {
            return getBusinessKnowledge(lowerQuery);
        }

        // Education & Learning
        if (lowerQuery.matches(".*(education|learning|university|college|exam|study|course|skill|knowledge|learning).*")) {
            return getEducationKnowledge(lowerQuery);
        }

        return "🌍 That's a fascinating question! Let me help you with that.\n\nI have comprehensive knowledge about:\n✅ World History - empires, revolutions, historical events\n✅ Geography - countries, cities, mountains, rivers\n✅ Science & Technology - physics, chemistry, space, AI, computers\n✅ Sports & Entertainment - famous games, movies, music\n✅ Famous People - scientists, leaders, artists, inventors\n✅ Nature & Wildlife - animals, ecosystems, environment\n✅ Business & Economics - companies, markets, startups\n✅ Education - universities, courses, skills\n\nAsk me anything! 😊";
    }

    private String getHistoryKnowledge(String query) {
        if (query.contains("india") || query.contains("bharat")) {
            return "🇮🇳 **Indian History**:\n\n• **Ancient India**: Mauryan Empire, Ashoka the Great, Vedic period\n• **Medieval**: Mughal Empire, Delhi Sultanate, Rajput kingdoms\n• **Modern**: British Raj, Independence (15 Aug 1947), Mahatma Gandhi, Jawaharlal Nehru\n• **Independence Struggle**: Non-violent movement by Gandhi, Subhas Chandra Bose, Freedom fighters\n• **Key Events**: Partition (1947), Constitution (1950), First Republic Day\n\n💡 India is the world's largest democracy with 1.4+ billion people! 🌟";
        } else if (query.contains("world") || query.contains("duniya")) {
            return "🌍 **World History Highlights**:\n\n• **Ancient Civilizations**: Egypt, Greece, Rome, Mesopotamia, Indus Valley\n• **Medieval Period**: European Dark Ages, Islamic Golden Age, Renaissance\n• **Age of Discovery**: Columbus, Vasco da Gama, Magellan\n• **Industrial Revolution**: Steam power, railways, factories (1760s)\n• **Modern Era**: World Wars, Independence movements, Technology revolution\n• **21st Century**: Digital transformation, globalization, space exploration\n\n⏰ Humanity has 10,000+ years of recorded history! 📚";
        }
        return "📚 **History is fascinating!**\n\nKey periods:\n• Ancient (3000 BC - 500 AD)\n• Medieval (500 - 1500 AD)\n• Modern (1500 - 1800)\n• Contemporary (1800 - Present)\n\nAsk me about specific events, people, or civilizations! 🏛️";
    }

    private String getGeographyKnowledge(String query) {
        if (query.contains("india")) {
            return "🇮🇳 **Geography of India**:\n\n📍 **Location**: South Asia, bordered by Arabian Sea, Bay of Bengal, Indian Ocean\n🏔️ **Mountains**: Himalayas (world's highest), Western & Eastern Ghats\n🌊 **Rivers**: Ganga, Brahmaputra, Yamuna, Indus (7 in top 10 world rivers)\n🏙️ **Capitals**: New Delhi (National), also has state capitals\n🗺️ **Area**: 3.28 million sq km (7th largest country)\n👥 **Population**: 1.4+ billion (most populous democracy)\n🌾 **Regions**: Diverse - deserts, mountains, coastlines, plateaus\n\n🌟 India has 28 states and 8 union territories!";
        } else if (query.contains("capital") || query.contains("country")) {
            return "🌍 **World Capitals & Countries**:\n\n🇺🇸 USA - Washington DC\n🇬🇧 UK - London\n🇫🇷 France - Paris\n🇩🇪 Germany - Berlin\n🇯🇵 Japan - Tokyo\n🇨🇭 China - Beijing\n🇷🇺 Russia - Moscow\n🇧🇷 Brazil - Brasília\n🇦🇺 Australia - Canberra\n🇨🇦 Canada - Ottawa\n\n🗺️ There are 195 countries in the world! Ask about any specific country! 🌏";
        }
        return "🗺️ **World Geography**:\n\n🌍 7 Continents: Asia, Africa, Europe, North America, South America, Oceania, Antarctica\n🌊 5 Oceans: Pacific, Atlantic, Indian, Arctic, Southern\n🏔️ Tallest Mountains: Mt. Everest (8,849m), K2, Kangchenjunga\n🌊 Longest Rivers: Nile (Africa), Amazon (South America), Yangtze (Asia)\n\nAsk about countries, regions, mountains, or anything geographic! 🧭";
    }

    private String getScienceTechnologyKnowledge(String query) {
        if (query.contains("space") || query.contains("planet") || query.contains("universe")) {
            return "🚀 **Space & Universe**:\n\n🌌 **Universe**: Contains billions of galaxies, each with billions of stars\n🪐 **Planets**: 8 in our Solar System - Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune\n🌍 **Earth**: 4.5 billion years old, only known planet with life\n⭐ **Sun**: Center of solar system, powers all life on Earth\n🌙 **Moon**: Earth's natural satellite, 384,400 km away\n\n🛸 **Space Exploration**:\n• First man on Moon (1969) - Neil Armstrong\n• International Space Station (ISS) - continuously occupied since 1998\n• Mars rovers exploring the red planet\n• NASA, ISRO, SpaceX, ESA leading exploration\n\n🔭 Universe is 13.8 billion years old!";
        } else if (query.contains("ai") || query.contains("artificial") || query.contains("technology")) {
            return "🤖 **Artificial Intelligence & Technology**:\n\n💻 **AI**: Machine learning, neural networks, pattern recognition\n📱 **Technology**: Internet, smartphones, cloud computing, IoT\n🔐 **Cybersecurity**: Protecting digital information\n⚡ **Speed of Light**: 3 lakh km/second (fastest in universe)\n🧬 **Biotechnology**: Gene editing, DNA sequencing\n\n🌟 **Future Technologies**:\n• Quantum computing\n• Blockchain & Cryptocurrency\n• Virtual Reality & Metaverse\n• 5G & 6G networks\n• Nanotechnology\n\n🚀 Technology evolving at exponential speed!";
        }
        return "🔬 **Science & Technology**:\n\n⚛️ **Physics**: Laws of motion, energy, gravity, quantum mechanics\n🧪 **Chemistry**: Elements, compounds, reactions\n🧬 **Biology**: Life, evolution, genetics, DNA\n🌍 **Earth Science**: Geology, meteorology, oceanography\n💻 **Technology**: Computing, Internet, AI, robotics\n\nAsk me about specific topics! 🔭";
    }

    private String getSportsEntertainmentKnowledge(String query) {
        if (query.contains("cricket")) {
            return "🏏 **Cricket**:\n\n• **Format**: Test (5 days), ODI (50 overs), T20 (20 overs)\n• **World Cups**: ICC Cricket World Cup every 4 years\n• **Recent Champions**: Australia (2023 ODI), West Indies, India, Pakistan\n• **Legends**: Sachin Tendulkar, Virat Kohli, Steve Smith, Ricky Ponting\n• **Major Tournament**: IPL (Indian Premier League) - world's richest league\n• **India's Glory**: Won World Cups in 1983, 2011\n\n🇮🇳 Cricket is religion in India! Billions of fans! ❤️";
        } else if (query.contains("football")) {
            return "⚽ **Football (Soccer)**:\n\n• **World Cup**: Held every 4 years, greatest sporting event\n• **Teams**: 11 players per side, 90 minutes match time\n• **Leagues**: Premier League (England), La Liga (Spain), Serie A (Italy), Bundesliga (Germany)\n• **Legends**: Pelé, Maradona, Messi, Ronaldo\n• **Recent Winners**: Argentina (2022 World Cup), France (2022 final)\n• **Clubs**: Real Madrid, Barcelona, Manchester United, Liverpool\n\n⚽ Watched by 4+ billion people worldwide! Most popular sport! 🌍";
        }
        return "🎬 **Sports & Entertainment**:\n\n⚽ Football - world's most popular sport\n🏏 Cricket - billion fans, especially in Asia\n🏀 Basketball - NBA (US), global following\n🎾 Tennis - Grand Slams, Wimbledon\n🏈 American Football - NFL, Super Bowl\n\n🎭 **Entertainment**:\n🎬 Movies - Bollywood, Hollywood, world cinema\n🎵 Music - Various genres, international stars\n🎮 Gaming - E-sports, video games, esports tournaments\n\nAsk about your favorite sport or entertainer! 🌟";
    }

    private String getFamousPeopleKnowledge(String query) {
        if (query.contains("gandhi") || query.contains("mahatma")) {
            return "🙏 **Mahatma Gandhi (1869-1948)**:\n\n• **Full Name**: Mohandas Karamchand Gandhi\n• **Role**: Father of Indian independence\n• **Philosophy**: Non-violence (Ahimsa), Civil Disobedience\n• **Key Actions**: Salt March (1930), Quit India Movement\n• **Achievements**: Led India to independence from British rule\n• **Quote**: \"Be the change you wish to see in the world\"\n• **Death**: Assassinated in 1948 by Nathuram Godse\n\n✨ One of the most influential leaders of 20th century! 🇮🇳";
        } else if (query.contains("einstein")) {
            return "🧪 **Albert Einstein (1879-1955)**:\n\n• **Nationality**: German-Swiss physicist\n• **Famous Theory**: Theory of Relativity (E=mc²)\n• **Impact**: Revolutionized physics and our understanding of universe\n• **Achievements**: Nobel Prize in Physics (1921)\n• **Quote**: \"Imagination is more important than knowledge\"\n• **Legacy**: Foundation of modern physics, nuclear energy\n\n⚛️ One of the greatest scientists of all time! 🌟";
        }
        return "👥 **Famous People Throughout History**:\n\n🧪 **Scientists**: Einstein, Newton, Curie, Hawking, Oppenheimer\n🏛️ **Leaders**: Gandhi, Churchill, Lincoln, Mandela, Kennedy\n🎨 **Artists**: Da Vinci, Michelangelo, Van Gogh, Picasso\n📚 **Authors**: Shakespeare, Tolstoy, Austen, Rowling\n⚽ **Sports**: Pelé, Messi, Ronaldo, Tendulkar\n🎬 **Actors**: Chaplin, Monroe, De Niro, Shah Rukh Khan\n\nAsk about any historical figure! 📖";
    }

    private String getNatureKnowledge(String query) {
        return "🌿 **Nature & Wildlife**:\n\n🦁 **Animals**: Over 8.7 million species on Earth\n🌳 **Plants**: Oxygen producers, food sources, medicines\n🦅 **Birds**: Most diverse vertebrates, 10,000+ species\n🐠 **Marine Life**: 70% of Earth covered by ocean, 200,000+ species\n🐆 **Endangered Species**: Panda, Bengal Tiger, Rhino, Elephant\n\n🌍 **Ecosystems**:\n🌴 Tropical Rainforests - \"Lungs of Earth\", Amazon rainforest\n🏔️ Mountains - Habitat for unique species\n🌊 Oceans - Coral reefs, underwater biodiversity\n🏜️ Deserts - Adapted life forms\n\n♻️ **Conservation**: Protecting biodiversity for future generations! 🌱";
    }

    private String getBusinessKnowledge(String query) {
        return "💼 **Business & Economics**:\n\n📊 **Concepts**: Market, supply-demand, profit, loss, revenue\n🏢 **Companies**: Apple, Microsoft, Google, Amazon, Tesla\n📈 **Stock Market**: NYSE, NSE (India), investing, trading\n🚀 **Startups**: Innovation, venture capital, unicorns\n💰 **Money**: Currency, cryptocurrency, blockchain\n🏦 **Banking**: Financial institutions, loans, savings\n\n🌍 **Global Economy**:\n• World GDP: ~$100 trillion+\n• Top Economies: USA, China, Japan, Germany, India\n• International Trade: Billions of dollars daily\n\n💡 **Entrepreneurship**: Risk, innovation, creating value! 🎯";
    }

    private String getEducationKnowledge(String query) {
        return "📚 **Education & Learning**:\n\n🎓 **Levels**: Primary, Secondary, Higher, Professional\n🏫 **Institutions**: Schools, Colleges, Universities\n📖 **Fields**: Science, Arts, Commerce, Engineering, Medicine\n💻 **Online Learning**: MOOCs, platforms like Coursera, edX\n🧠 **Skills**: Critical thinking, communication, problem-solving\n\n🌟 **Famous Universities**:\n• Harvard, MIT, Stanford (USA)\n• Oxford, Cambridge (UK)\n• IIT, DU (India)\n• Tokyo University (Japan)\n\n🎯 **Learning Tips**:\n✅ Consistency is key\n✅ Practice regularly\n✅ Learn from failures\n✅ Stay curious\n\n🚀 Knowledge is power! Keep learning! 📚";
    }



    private boolean isHelpQuery(String query) {
        return query.matches(".*(help|kya kar sakte|kya karne|support|batao|bata).*") && 
               (query.contains("help") || query.contains("kya") || query.contains("batao"));
    }

    private boolean isProductQuery(String query) {
        return query.matches(".*(product|item|phone|laptop|watch|shirt|shoe|device|gadget|saman|lao|recommend|suggest|dikhao|kaunsa).*");
    }

    private boolean isOrderQuery(String query) {
        return query.matches(".*(order|delivery|track|shipped|delivered|kab aayega|status|mere order).*");
    }

    private boolean isPriceQuery(String query) {
        return query.matches(".*(price|cost|rupee|rs|cheap|expensive|deal|discount|offer|best|sasta|mehenga).*");
    }

    private String getProductRecommendations(String query) {
        List<Product> allProducts = productRepository.findAll();
        
        if (allProducts.isEmpty()) {
            return "Sorry, I don't have any products available right now. Please check back soon! 🛍️";
        }

        // Extract product type from query
        String recommendation = "Great choice! Here are my top recommendations:\n\n";
        
        int count = 0;
        for (Product product : allProducts) {
            if (count >= 3) break;
            
            String productName = product.getName().toLowerCase();
            String description = product.getDescription() != null ? product.getDescription().toLowerCase() : "";
            
            if (productName.contains(extractKeyword(query)) || description.contains(extractKeyword(query))) {
                recommendation += String.format("🌟 **%s** - ₹%.2f\n   Rating: %.1f/5 ⭐\n   %s\n\n", 
                    product.getName(), 
                    product.getPrice(),
                    product.getRating() != null ? product.getRating().doubleValue() : 0.0,
                    description.isEmpty() ? "Premium quality product" : description
                );
                count++;
            }
        }

        if (count == 0) {
            return "I couldn't find specific recommendations for that, but here are our top products:\n\n" + 
                   allProducts.stream()
                       .limit(3)
                       .map(p -> String.format("🌟 %s - ₹%.2f", p.getName(), p.getPrice()))
                       .collect(Collectors.joining("\n")) + 
                   "\n\nWould you like to know more about any of these?";
        }

        return recommendation + "💡 Add any of these to your cart to get started! Would you like more options?";
    }

    private String getOrderInfo(Long userId) {
        List<Order> userOrders = orderRepository.findByUser_Id(userId);
        
        if (userOrders.isEmpty()) {
            return "You haven't placed any orders yet! 📦\n\nBut don't worry, I can help you find amazing products to shop! What are you looking for?";
        }

        String orderInfo = "📦 **Your Recent Orders**:\n\n";
        
        int count = 0;
        for (Order order : userOrders) {
            if (count >= 3) break;
            
            String status = order.getStatus() != null ? order.getStatus() : "PENDING";
            String statusEmoji = status.equals("DELIVERED") ? "✅" : 
                                status.equals("SHIPPED") ? "🚚" : "⏳";
            
            orderInfo += String.format("%s **Order #%d** - ₹%.2f (%s)\n", 
                statusEmoji, order.getId(), order.getTotalAmount(), status);
            count++;
        }

        return orderInfo + "\n💡 Would you like tracking details for any order?";
    }

    private String getBestDeals() {
        List<Product> allProducts = productRepository.findAll();
        
        if (allProducts.isEmpty()) {
            return "No products available right now! 😕";
        }

        StringBuilder deals = new StringBuilder("🔥 **Hot Deals & Best Prices** 🔥\n\n");
        
        allProducts.stream()
            .filter(p -> p.getPrice() != null && p.getPrice() < 50000)
            .sorted((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
            .limit(3)
            .forEach(p -> deals.append(String.format("💰 %s - ₹%.2f ⭐%.1f\n", 
                p.getName(), p.getPrice(), p.getRating() != null ? p.getRating() : 0.0)));

        return deals + "\n👉 Click on any product to buy now and get them delivered at your doorstep!";
    }

    private String generateGenericResponse(String query) {
        String[] genericResponses = {
            "That's a great question! 🤔 I can help you with:\n- Finding products\n- Tracking orders\n- Getting best deals\n- Any shopping questions\n\nWhat would you like to do?",
            "Interesting! 💭 Let me help you with that. Try asking me about:\n✅ Specific products\n✅ Your order status\n✅ Best deals\n✅ Product recommendations",
            "I'm here to help! 😊 You can ask me about:\n🛍️ Products\n📦 Orders\n💰 Prices\n⭐ Ratings\n\nWhat can I find for you?",
            "Great question! 🌟 I'm VIYA, your shopping assistant. Ask me about products, orders, deals, or anything related to shopping!"
        };

        return genericResponses[(int)(System.currentTimeMillis() % genericResponses.length)];
    }

    private String extractKeyword(String query) {
        // Remove common words and extract main keyword
        String[] words = query.replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");
        for (String word : words) {
            if (word.length() > 3 && !isCommonWord(word.toLowerCase())) {
                return word.toLowerCase();
            }
        }
        return "";
    }

    private boolean isCommonWord(String word) {
        return word.matches(".*(phone|laptop|watch|shirt|shoe|device|gadget|product|kya|hai|aur|se|ka|ke|ki|hain|dikhao|batao|saman).*");
    }

    /**
     * Get AI greeting based on time of day
     */
    public String getGreeting() {
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();

        String greeting;
        if (hour < 12) {
            greeting = "Good Morning! ☀️";
        } else if (hour < 18) {
            greeting = "Good Afternoon! 🌤️";
        } else {
            greeting = "Good Evening! 🌙";
        }

        return greeting + " I'm VIYA! How can I assist you today? 😊";
    }

    /**
     * Get quick tips for shopping
     */
    public String getShoppingTips() {
        String[] tips = {
            "💡 **Pro Tip**: Check out our deals section for amazing discounts! 🔥",
            "💡 **Pro Tip**: Sign up for notifications to get alerts on your favorite products! 🔔",
            "💡 **Pro Tip**: Read customer reviews before buying - they help! ⭐",
            "💡 **Pro Tip**: Use filters to find exactly what you're looking for! 🎯",
            "💡 **Pro Tip**: Track your orders in real-time to know when they arrive! 📦"
        };

        return tips[(int)(System.currentTimeMillis() % tips.length)];
    }

    /**
     * Get AI personality info
     */
    public String getAboutVIYA() {
        return "🤖 **About VIYA**\n\n" +
               "I'm VIYA - **Virtual Intelligent Your Assistant** 🎯\n\n" +
               "My superpowers:\n" +
               "✨ Understand your voice commands\n" +
               "✨ Find perfect products for you\n" +
               "✨ Track your deliveries in real-time\n" +
               "✨ Give honest product recommendations\n" +
               "✨ Help with any shopping questions\n\n" +
               "I'm powered by advanced AI to make your shopping experience amazing! 🌟";
    }
}
