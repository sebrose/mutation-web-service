require 'checkout'

RSpec.configure do |config|
  # Use RSpec 3 expect matching syntax
  config.expect_with :rspec do |expect_with|
    expect_with.syntax = :expect
  end
end
