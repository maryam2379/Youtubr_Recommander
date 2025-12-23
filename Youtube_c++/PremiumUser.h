#ifndef PREMIUMUSER_H
#define PREMIUMUSER_H

#include "User.h"

class PremiumUser : public User {
public:
    std::string paymentSubscript;
    
    PremiumUser();
    PremiumUser(const std::string& name, const std::string& gender, int numTel, 
                const std::string& email, const std::string& password);
    
    static PremiumUser createPremiumUser(const std::string& name, const std::string& gender, 
                                        int numTel, const std::string& email, 
                                        const std::string& password, const std::string& option);
    
    void subscriptionPayment();
    void displayProfilePremiumUser() const;
    void saveToFile() override;
    static PremiumUser loadPremiumFromFile(const std::string& userId);
    
    std::string getPaymentSubscript() const { return paymentSubscript; }
    void setPaymentSubscript(const std::string& ps) { paymentSubscript = ps; }
};

#endif